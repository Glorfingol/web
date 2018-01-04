package com.cmpl.web.news;

import org.springframework.cache.annotation.CacheConfig;

import com.cmpl.web.core.service.BaseServiceImpl;

/**
 * Implementation de l'interface de gestion des NewsImage
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = {"modelPage"})
public class NewsImageServiceImpl extends BaseServiceImpl<NewsImageDTO, NewsImage> implements NewsImageService {

  public NewsImageServiceImpl(NewsImageRepository newsImageRepository) {
    super(newsImageRepository);
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
