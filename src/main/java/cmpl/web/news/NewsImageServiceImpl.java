package cmpl.web.news;

import cmpl.web.core.service.BaseServiceImpl;

/**
 * Implementation de l'interface de gestion des NewsImage
 * 
 * @author Louis
 *
 */
public class NewsImageServiceImpl extends BaseServiceImpl<NewsImageDTO, NewsImage> implements NewsImageService {

  private NewsImageServiceImpl(NewsImageRepository newsImageRepository) {
    super(newsImageRepository);
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param newsImageRepository
   * @return
   */
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
