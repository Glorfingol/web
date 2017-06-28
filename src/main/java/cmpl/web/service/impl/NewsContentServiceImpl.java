package cmpl.web.service.impl;

import cmpl.web.model.news.dao.NewsContent;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.repository.NewsContentRepository;
import cmpl.web.service.NewsContentService;

/**
 * Implementation de l'interface pour la gestion de NewsContent
 * 
 * @author Louis
 *
 */
public class NewsContentServiceImpl extends BaseServiceImpl<NewsContentDTO, NewsContent> implements NewsContentService {

  private NewsContentServiceImpl(NewsContentRepository newsContentRepository) {
    super(newsContentRepository);
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param newsContentRepository
   * @return
   */
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
