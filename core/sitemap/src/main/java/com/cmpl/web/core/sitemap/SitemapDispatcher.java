package com.cmpl.web.core.sitemap;

import com.cmpl.web.core.common.exception.BaseException;
import java.util.Locale;

public interface SitemapDispatcher {

  SitemapResponse createEntity(SitemapCreateForm createForm, Locale locale) throws BaseException;

  void deleteEntity(Long websiteId, Long pageId) throws BaseException;

}
