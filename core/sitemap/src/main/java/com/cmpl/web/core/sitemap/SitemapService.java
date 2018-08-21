package com.cmpl.web.core.sitemap;

import com.cmpl.web.core.common.service.BaseService;
import java.util.List;

public interface SitemapService extends BaseService<SitemapDTO> {

  List<SitemapDTO> findByWebsiteId(Long websiteId);

  List<SitemapDTO> findByPageId(Long pageId);

  SitemapDTO findByWebsiteIdAndPageId(Long websiteId, Long pageId);

}
