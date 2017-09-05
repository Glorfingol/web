package cmpl.web.news;

import cmpl.web.core.service.BaseServiceImpl;

/**
 * Implementation de l'interface pour la gestion de NewsContent
 * 
 * @author Louis
 *
 */
public class NewsContentServiceImpl extends BaseServiceImpl<NewsContentDTO, NewsContent> implements NewsContentService {

  public NewsContentServiceImpl(NewsContentRepository newsContentRepository) {
    super(newsContentRepository);
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
