package cmpl.web.service.impl;

import cmpl.web.model.news.dao.NewsContent;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.repository.NewsContentRepository;
import cmpl.web.service.NewsContentService;

public class NewsContentServiceImpl extends BaseServiceImpl<NewsContentDTO, NewsContent> implements NewsContentService {

  private NewsContentServiceImpl(NewsContentRepository newsContentRepository) {
    super(newsContentRepository);
  }

  public static NewsContentServiceImpl fromRepositories(NewsContentRepository newsContentRepository) {
    return new NewsContentServiceImpl(newsContentRepository);
  }

  @Override
  protected NewsContentDTO toDTO(NewsContent entity) {

    NewsContentDTO dto = new NewsContentDTO();
    fillObject(entity, dto);

    return dto;
  }

  @Override
  protected NewsContent toEntity(NewsContentDTO dto) {

    NewsContent entity = new NewsContent();
    fillObject(dto, entity);

    return entity;
  }

}
