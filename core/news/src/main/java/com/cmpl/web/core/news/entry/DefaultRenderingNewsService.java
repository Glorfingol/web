package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.common.service.DefaultReadOnlyService;
import com.cmpl.web.core.models.NewsEntry;

public class DefaultRenderingNewsService extends
  DefaultReadOnlyService<RenderingNewsEntryDTO, NewsEntry> implements
  RenderingNewsService {

  public DefaultRenderingNewsService(NewsEntryDAO dao, RenderingNewsEntryMapper mapper) {
    super(dao, mapper);
  }


}
