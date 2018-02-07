package com.cmpl.web.core.news;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.file.ImageConverterService;
import com.cmpl.web.core.file.ImageService;

/**
 * Implementation de l'interface pour la gestion des NewsEntry
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = "newsEntries")
public class NewsEntryServiceImpl extends BaseServiceImpl<NewsEntryDTO, NewsEntry> implements NewsEntryService {

  private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryServiceImpl.class);

  private final NewsEntryRepository newsEntryRepository;
  private final NewsImageService newsImageService;
  private final NewsContentService newsContentService;
  private final ImageConverterService imageConverterService;
  private final ImageService imageService;

  public NewsEntryServiceImpl(NewsEntryRepository newsEntryRepository, NewsImageService newsImageService,
      NewsContentService newsContentService, ImageConverterService imageConverterService, ImageService imageService) {
    super(newsEntryRepository);
    this.newsEntryRepository = newsEntryRepository;
    this.newsImageService = newsImageService;
    this.newsContentService = newsContentService;
    this.imageConverterService = imageConverterService;
    this.imageService = imageService;
  }

  @Override
  @Transactional
  @CacheEvict(value = {"listedNewsEntries", "pagedNewsEntries"}, allEntries = true)
  public NewsEntryDTO createEntity(NewsEntryDTO dto) {

    LOGGER.info("Creation d'une nouvelle entrée de blog");

    NewsEntry entry = new NewsEntry();
    fillObject(dto, entry);

    String contentId = createContent(dto.getNewsContent());
    entry.setContentId(contentId);

    String imageId = createImage(dto.getNewsImage());
    entry.setImageId(imageId);

    return toDTO(newsEntryRepository.save(entry));
  }

  String createContent(NewsContentDTO contentToCreate) {
    String contentId = "";
    if (contentToCreate != null) {
      contentId = String.valueOf(newsContentService.createEntity(contentToCreate).getId());
    }
    return contentId;
  }

  String createImage(NewsImageDTO imageToCreate) {
    String imageId = "";
    if (imageToCreate != null) {
      NewsImageDTO formattedImage = formatImage(imageToCreate);
      imageId = String.valueOf(newsImageService.createEntity(formattedImage).getId());
      File savedFile = saveToFileSystem(imageToCreate, imageId);

      NewsImageDTO imageToUpdate = newsImageService.getEntity(Long.valueOf(imageId));
      imageToUpdate.setSrc(computeImageSrc(savedFile));
      newsImageService.updateEntity(imageToUpdate);

    }
    return imageId;
  }

  File saveToFileSystem(NewsImageDTO imageToCreate, String imageId) {
    try {
      return imageService.saveFileOnSystem(imageId, imageToCreate.getBase64Src());
    } catch (BaseException e) {
      LOGGER.error("Impossible d'enregistrer l'image sur le filesystem", e);
    }
    return null;
  }

  @Override
  @Transactional
  @CachePut(key = "#a0.id")
  @CacheEvict(value = {"listedNewsEntries", "pagedNewsEntries"}, allEntries = true)
  public NewsEntryDTO updateEntity(NewsEntryDTO dto) {

    LOGGER.info("Mise à jour d'une entrée de blog d'id " + dto.getId());
    NewsEntry entry = new NewsEntry();
    fillObject(dto, entry);

    NewsContentDTO contentToUpdate = updateContent(dto.getNewsContent());
    String contentId = contentToUpdate == null ? "" : String.valueOf(contentToUpdate.getId());
    entry.setContentId(contentId);

    NewsImageDTO imageToUpdate = updateImage(dto.getNewsImage());
    String imageId = imageToUpdate == null ? "" : String.valueOf(imageToUpdate.getId());
    entry.setImageId(imageId);

    NewsEntryDTO dtoUpdated = toDTO(newsEntryRepository.save(entry));
    dtoUpdated.setNewsContent(contentToUpdate);
    dtoUpdated.setNewsImage(imageToUpdate);

    return dtoUpdated;
  }

  NewsContentDTO updateContent(NewsContentDTO contentToUpdate) {
    if (contentToUpdate != null) {
      return dealWithContentToUpdate(contentToUpdate);
    }
    return contentToUpdate;
  }

  NewsImageDTO updateImage(NewsImageDTO imageToUpdate) {
    if (imageToUpdate != null) {
      NewsImageDTO formattedImage = formatImage(imageToUpdate);
      return dealWithImageToUpdate(imageToUpdate, formattedImage);
    }
    return imageToUpdate;
  }

  NewsContentDTO dealWithContentToUpdate(NewsContentDTO contentToUpdate) {
    NewsContentDTO contentSaved;
    Long contentId = contentToUpdate.getId();
    if (contentId == null) {
      contentSaved = newsContentService.createEntity(contentToUpdate);
    } else {
      contentSaved = newsContentService.updateEntity(contentToUpdate);
    }
    return contentSaved;
  }

  NewsImageDTO dealWithImageToUpdate(NewsImageDTO imageToUpdate, NewsImageDTO formattedImage) {
    NewsImageDTO imageSaved;
    Long imageToUpdateId = imageToUpdate.getId();
    if (imageToUpdateId == null) {
      imageSaved = newsImageService.createEntity(formattedImage);
    } else {
      imageSaved = newsImageService.updateEntity(formattedImage);
    }
    File savedFile = saveToFileSystem(imageToUpdate, String.valueOf(imageSaved.getId()));
    imageSaved.setSrc(computeImageSrc(savedFile));
    newsImageService.updateEntity(imageSaved);
    return imageSaved;
  }

  NewsImageDTO formatImage(NewsImageDTO imageToUpdate) {

    BufferedImage bufferedImage = imageConverterService.computeNewsImageFromString(imageToUpdate.getBase64Src());

    NewsImageDTO formattedImage = NewsImageDTOBuilder.create().width(computeWidth(bufferedImage))
        .height(computeHeight(bufferedImage)).alt(imageToUpdate.getAlt()).legend(imageToUpdate.getLegend())
        .id(imageToUpdate.getId()).creationDate(imageToUpdate.getCreationDate())
        .modificationDate(imageToUpdate.getModificationDate()).build();
    return formattedImage;
  }

  int computeWidth(BufferedImage bufferedImage) {
    if (bufferedImage == null) {
      return 0;
    }
    return bufferedImage.getWidth();
  }

  int computeHeight(BufferedImage bufferedImage) {
    if (bufferedImage == null) {
      return 0;
    }
    return bufferedImage.getHeight();
  }

  @Override
  @Cacheable(key = "#a0")
  public NewsEntryDTO getEntity(Long id) {
    LOGGER.info("Récupération de l'entrée de blog d'id " + id);
    NewsEntry entry = newsEntryRepository.findById(id).get();
    return computeNewsEntryDTO(entry);
  }

  @Override
  @Cacheable(value = "listedNewsEntries")
  public List<NewsEntryDTO> getEntities() {

    LOGGER.info("Récupération de toutes les entrées de blog");
    List<NewsEntryDTO> entries = new ArrayList<>();
    List<NewsEntry> newsEntries = newsEntryRepository.findAll();

    newsEntries.forEach(newsEntry -> entries.add(computeNewsEntryDTO(newsEntry)));

    return entries;
  }

  @Override
  protected Page<NewsEntryDTO> toPageDTO(Page<NewsEntry> pagedNewsEntries, PageRequest pageRequest) {

    List<NewsEntryDTO> dtos = new ArrayList<>();

    pagedNewsEntries.getContent().forEach(entity -> dtos.add(computeNewsEntryDTO(entity)));

    return new PageImpl<>(dtos, pageRequest, pagedNewsEntries.getTotalElements());

  }

  NewsEntryDTO computeNewsEntryDTO(NewsEntry newsEntry) {
    NewsEntryDTO newsEntryDTO = toDTO(newsEntry);

    String newsContentId = newsEntry.getContentId();
    if (StringUtils.hasText(newsContentId)) {
      newsEntryDTO.setNewsContent(newsContentService.getEntity(Long.parseLong(newsContentId)));
    }

    String newsImageId = newsEntry.getImageId();
    if (StringUtils.hasText(newsImageId)) {
      NewsImageDTO image = newsImageService.getEntity(Long.parseLong(newsImageId));
      newsEntryDTO.setNewsImage(image);

    }

    return newsEntryDTO;
  }

  @Override
  protected NewsEntryDTO toDTO(NewsEntry entity) {

    NewsEntryDTO dto = new NewsEntryDTO();
    fillObject(entity, dto);

    return dto;
  }

  @Override
  protected NewsEntry toEntity(NewsEntryDTO dto) {

    NewsEntry entity = new NewsEntry();
    fillObject(dto, entity);

    return entity;
  }

  String computeImageSrc(File file) {
    String filePath = file.getPath();
    int firstIndex = filePath.indexOf("img");
    filePath = filePath.substring(firstIndex, filePath.length());
    return filePath;
  }

  @Override
  public boolean isAlreadyImportedFromFacebook(String facebookId) {
    return !CollectionUtils.isEmpty(newsEntryRepository.findByFacebookId(facebookId));
  }

  @Override
  @Cacheable(value = "pagedNewsEntries")
  public Page<NewsEntryDTO> getPagedEntities(PageRequest pageRequest) {
    return super.getPagedEntities(pageRequest);
  }

}
