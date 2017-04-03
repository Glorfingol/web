package cmpl.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import cmpl.web.model.news.dao.NewsEntry;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.repository.NewsEntryRepository;
import cmpl.web.service.NewsContentService;
import cmpl.web.service.NewsEntryService;
import cmpl.web.service.NewsImageService;

public class NewsEntryServiceImpl extends BaseServiceImpl<NewsEntryDTO, NewsEntry> implements NewsEntryService {

  private final NewsEntryRepository newsEntryRepository;
  private final NewsImageService newsImageService;
  private final NewsContentService newsContentService;

  private static final long DAY_IN_MS = 1000 * 60 * 60 * 24;

  private NewsEntryServiceImpl(NewsEntryRepository newsEntryRepository, NewsImageService newsImageService,
      NewsContentService newsContentService) {
    super(newsEntryRepository);
    this.newsEntryRepository = newsEntryRepository;
    this.newsImageService = newsImageService;
    this.newsContentService = newsContentService;
  }

  public static NewsEntryServiceImpl fromRepositoriesAndServices(NewsEntryRepository newsEntryRepository,
      NewsImageService newsImageService, NewsContentService newsContentService) {
    return new NewsEntryServiceImpl(newsEntryRepository, newsImageService, newsContentService);
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
      newsEntryDTO.setNewsImage(newsImageService.getEntity(Long.parseLong(newsImageId)));
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
