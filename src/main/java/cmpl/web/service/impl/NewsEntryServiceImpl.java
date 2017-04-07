package cmpl.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

  private static final long DAY_IN_MS = 1000 * 60 * 60 * 24;

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
    NewsContentDTO contentToCreate = dto.getNewsContent();
    if (contentToCreate != null) {
      entry.setContentId(String.valueOf(newsContentService.createEntity(contentToCreate).getId()));
    }
    NewsImageDTO imageToCreate = dto.getNewsImage();
    if (imageToCreate != null) {
      NewsImageDTO formattedImage = imageConverterService.computeNewsImageFromString(imageToCreate.getBase64Src());
      formattedImage.setAlt(imageToCreate.getAlt());
      formattedImage.setLegend(imageToCreate.getLegend());
      entry.setImageId(String.valueOf(newsImageService.createEntity(formattedImage).getId()));
    }

    return toDTO(newsEntryRepository.save(entry));
  }

  @Override
  public NewsEntryDTO updateEntity(NewsEntryDTO dto) {
    NewsEntry entry = new NewsEntry();
    fillObject(dto, entry);

    NewsContentDTO contentToUpdate = dto.getNewsContent();
    if (contentToUpdate != null) {

      String contentId = String.valueOf(contentToUpdate.getId());
      if (StringUtils.isEmpty(contentId)) {
        contentToUpdate = newsContentService.createEntity(contentToUpdate);
        entry.setContentId(String.valueOf(contentToUpdate.getId()));
      } else {
        contentToUpdate = newsContentService.updateEntity(contentToUpdate);
      }

    }

    NewsImageDTO imageToUpdate = dto.getNewsImage();
    if (imageToUpdate != null) {
      NewsImageDTO formattedImage = imageConverterService.computeNewsImageFromString(imageToUpdate.getBase64Src());
      formattedImage.setAlt(imageToUpdate.getAlt());
      formattedImage.setLegend(imageToUpdate.getLegend());
      formattedImage.setId(imageToUpdate.getId());
      formattedImage.setCreationDate(imageToUpdate.getCreationDate());
      formattedImage.setModificationDate(imageToUpdate.getModificationDate());

      String imageToUpdateId = String.valueOf(imageToUpdate.getId());
      if (StringUtils.isEmpty(imageToUpdateId)) {
        imageToUpdate = newsImageService.createEntity(imageToUpdate);
        entry.setImageId(String.valueOf(imageToUpdate.getId()));
      } else {
        imageToUpdate = newsImageService.updateEntity(imageToUpdate);
      }

    }

    NewsEntryDTO dtoUpdated = toDTO(newsEntryRepository.save(entry));
    dtoUpdated.setNewsContent(contentToUpdate);
    dtoUpdated.setNewsImage(imageToUpdate);

    return dtoUpdated;
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

  @Override
  public List<NewsEntryDTO> getRecentNews() {

    List<NewsEntryDTO> newsEntriesToReturn = new ArrayList<NewsEntryDTO>();

    Calendar today = Calendar.getInstance();
    Date tenDaysAgo = new Date(today.getTimeInMillis() - (7 * DAY_IN_MS));
    List<NewsEntry> newsEntries = newsEntryRepository.findByCreationDateBetween(tenDaysAgo, today.getTime());

    for (NewsEntry newsEntry : newsEntries) {
      newsEntriesToReturn.add(computeNewsEntryDTO(newsEntry));
    }

    return newsEntriesToReturn;
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
