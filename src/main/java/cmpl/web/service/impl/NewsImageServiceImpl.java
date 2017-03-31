package cmpl.web.service.impl;

import cmpl.web.model.news.dao.NewsImage;
import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.repository.NewsImageRepository;
import cmpl.web.service.NewsImageService;

public class NewsImageServiceImpl extends BaseServiceImpl<NewsImageDTO, NewsImage> implements NewsImageService {

  private NewsImageServiceImpl(NewsImageRepository newsImageRepository) {
    super(newsImageRepository);
  }

  public static NewsImageServiceImpl fromRepositories(NewsImageRepository newsImageRepository) {
    return new NewsImageServiceImpl(newsImageRepository);
  }

  @Override
  protected NewsImageDTO toDTO(NewsImage entity) {

    NewsImageDTO dto = new NewsImageDTO();
    fillObject(entity, dto);

    return dto;
  }

  @Override
  protected NewsImage toEntity(NewsImageDTO dto) {

    NewsImage entity = new NewsImage();
    fillObject(dto, entity);

    return entity;
  }

}
