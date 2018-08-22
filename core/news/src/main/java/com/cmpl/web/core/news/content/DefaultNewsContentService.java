package com.cmpl.web.core.news.content;

import com.cmpl.web.core.common.service.DefaultBaseService;
import com.cmpl.web.core.models.NewsContent;
import org.springframework.cache.annotation.CacheConfig;

/**
 * Implementation de l'interface pour la gestion de NewsContent
 *
 * @author Louis
 */
@CacheConfig(cacheNames = "newsContents")
public class DefaultNewsContentService extends
    DefaultBaseService<NewsContentDTO, NewsContent> implements
    NewsContentService {

  public DefaultNewsContentService(NewsContentDAO newsContentDAO,
      NewsContentMapper newsContentMapper) {
    super(newsContentDAO, newsContentMapper);
  }

}
