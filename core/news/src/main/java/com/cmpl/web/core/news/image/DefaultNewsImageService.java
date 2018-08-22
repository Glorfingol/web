package com.cmpl.web.core.news.image;

import com.cmpl.web.core.common.service.DefaultBaseService;
import com.cmpl.web.core.models.NewsImage;
import org.springframework.cache.annotation.CacheConfig;

/**
 * Implementation de l'interface de gestion des NewsImage
 *
 * @author Louis
 */
@CacheConfig(cacheNames = "newsImages")
public class DefaultNewsImageService extends DefaultBaseService<NewsImageDTO, NewsImage> implements
    NewsImageService {

  public DefaultNewsImageService(NewsImageDAO newsImageDAO, NewsImageMapper newsImageMapper) {
    super(newsImageDAO, newsImageMapper);
  }

}
