package com.cmpl.web.core.news.content;

import com.cmpl.web.core.common.service.DefaultBaseService;
import com.cmpl.web.core.models.NewsContent;

/**
 * Implementation de l'interface pour la gestion de NewsContent
 *
 * @author Louis
 */
public class DefaultNewsContentService extends
    DefaultBaseService<NewsContentDTO, NewsContent> implements
    NewsContentService {

  public DefaultNewsContentService(NewsContentDAO newsContentDAO,
      NewsContentMapper newsContentMapper) {
    super(newsContentDAO, newsContentMapper);
  }

}
