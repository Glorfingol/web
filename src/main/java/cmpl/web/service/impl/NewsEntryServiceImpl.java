package cmpl.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import cmpl.web.model.news.dao.NewsEntry;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.repository.NewsEntryRepository;
import cmpl.web.service.ImageConverterService;
import cmpl.web.service.NewsContentService;
import cmpl.web.service.NewsEntryService;
import cmpl.web.service.NewsImageService;

public class NewsEntryServiceImpl extends BaseServiceImpl<NewsEntryDTO, NewsEntry> implements NewsEntryService {

  private final NewsEntryRepository newsEntryRepository;
  private final NewsImageService newsImageService;
  private final NewsContentService newsContentService;
  private final ImageConverterService imageConverterService;

  private NewsEntryServiceImpl(NewsEntryRepository newsEntryRepository, NewsImageService newsImageService,
      NewsContentService newsContentService, ImageConverterService imageConverterService) {
    super(newsEntryRepository);
    this.newsEntryRepository = newsEntryRepository;
    this.newsImageService = newsImageService;
    this.newsContentService = newsContentService;
    this.imageConverterService = imageConverterService;
  }

  public static NewsEntryServiceImpl fromRepositoriesAndServices(NewsEntryRepository newsEntryRepository,
      NewsImageService newsImageService, NewsContentService newsContentService,
      ImageConverterService imageConverterService) {
    return new NewsEntryServiceImpl(newsEntryRepository, newsImageService, newsContentService, imageConverterService);
  }

  @Override
  public NewsEntryDTO createEntity(NewsEntryDTO dto) {

    NewsEntry entry = new NewsEntry();
    fillObject(dto, entry);

    String contentId = createContent(dto.getNewsContent());
    entry.setContentId(contentId);

    String imageId = createImage(dto.getNewsImage());
    entry.setImageId(imageId);

    return toDTO(newsEntryRepository.save(entry));
  }

  private String createContent(NewsContentDTO contentToCreate) {
    String contentId = "";
    if (contentToCreate != null) {
      contentId = String.valueOf(newsContentService.createEntity(contentToCreate).getId());
    }
    return contentId;
  }

  private String createImage(NewsImageDTO imageToCreate) {
    String imageId = "";
    if (imageToCreate != null) {
      NewsImageDTO formattedImage = formatImage(imageToCreate);
      imageId = String.valueOf(newsImageService.createEntity(formattedImage).getId());
    }
    return imageId;
  }

  @Override
  public NewsEntryDTO updateEntity(NewsEntryDTO dto) {
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

  private NewsContentDTO updateContent(NewsContentDTO contentToUpdate) {
    if (contentToUpdate != null) {
      return dealWithContentToUpdate(contentToUpdate);
    }
    return contentToUpdate;
  }

  private NewsImageDTO updateImage(NewsImageDTO imageToUpdate) {
    if (imageToUpdate != null) {
      NewsImageDTO formattedImage = formatImage(imageToUpdate);
      return dealWithImageToUpdate(imageToUpdate, formattedImage);
    }
    return imageToUpdate;
  }

  private NewsContentDTO dealWithContentToUpdate(NewsContentDTO contentToUpdate) {
    NewsContentDTO contentSaved;
    Long contentId = contentToUpdate.getId();
    if (contentId == null) {
      contentSaved = newsContentService.createEntity(contentToUpdate);
    } else {
      contentSaved = newsContentService.updateEntity(contentToUpdate);
    }
    return contentSaved;
  }

  private NewsImageDTO dealWithImageToUpdate(NewsImageDTO imageToUpdate, NewsImageDTO formattedImage) {
    NewsImageDTO imageSaved;
    Long imageToUpdateId = imageToUpdate.getId();
    if (imageToUpdateId == null) {
      imageSaved = newsImageService.createEntity(formattedImage);
    } else {
      imageSaved = newsImageService.updateEntity(formattedImage);
    }
    return imageSaved;
  }

  private NewsImageDTO formatImage(NewsImageDTO imageToUpdate) {
    NewsImageDTO formattedImage = imageConverterService.computeNewsImageFromString(imageToUpdate.getBase64Src());
    formattedImage.setAlt(imageToUpdate.getAlt());
    formattedImage.setLegend(imageToUpdate.getLegend());
    formattedImage.setId(imageToUpdate.getId());
    formattedImage.setCreationDate(imageToUpdate.getCreationDate());
    formattedImage.setModificationDate(imageToUpdate.getModificationDate());
    return formattedImage;
  }

  @Override
  public NewsEntryDTO getEntity(Long id) {
    NewsEntry entry = newsEntryRepository.findOne(id);
    return computeNewsEntryDTO(entry);
  }

  @Override
  public List<NewsEntryDTO> getEntities() {

    List<NewsEntryDTO> entries = new ArrayList<NewsEntryDTO>();
    List<NewsEntry> newsEntries = newsEntryRepository.findAll();

    for (NewsEntry newsEntry : newsEntries) {
      entries.add(computeNewsEntryDTO(newsEntry));
    }

    return entries;
  }

  NewsEntryDTO computeNewsEntryDTO(NewsEntry newsEntry) {
    NewsEntryDTO newsEntryDTO = toDTO(newsEntry);

    String newsContentId = newsEntry.getContentId();
    if (!StringUtils.isEmpty(newsContentId)) {
      newsEntryDTO.setNewsContent(newsContentService.getEntity(Long.parseLong(newsContentId)));
    }

    String newsImageId = newsEntry.getImageId();
    if (!StringUtils.isEmpty(newsImageId)) {
      NewsImageDTO image = newsImageService.getEntity(Long.parseLong(newsImageId));
      image.setBase64Src(imageConverterService.convertByteArrayToBase64(image.getSrc(), image.getFormat()));
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

}
