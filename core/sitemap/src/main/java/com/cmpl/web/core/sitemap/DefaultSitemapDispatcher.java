package com.cmpl.web.core.sitemap;

import com.cmpl.web.core.common.exception.BaseException;
import java.util.Locale;
import java.util.Objects;

public class DefaultSitemapDispatcher implements SitemapDispatcher {

  private final SitemapService service;

  private final SitemapTranslator translator;

  public DefaultSitemapDispatcher(SitemapService service, SitemapTranslator translator) {

    this.service = Objects.requireNonNull(service);

    this.translator = Objects.requireNonNull(translator);

  }

  @Override
  public SitemapResponse createEntity(SitemapCreateForm createForm, Locale locale)
      throws BaseException {

    SitemapDTO sitemapDTOToCreate = translator.fromCreateFormToDTO(createForm);
    SitemapDTO createdSitemapDTO = service.createEntity(sitemapDTOToCreate);

    return translator.fromDTOToResponse(createdSitemapDTO);
  }

  @Override
  public void deleteEntity(Long websiteId, Long pageId) throws BaseException {

    SitemapDTO sitemapDTO = service.findByWebsiteIdAndPageId(websiteId, pageId);
    service.deleteEntity(sitemapDTO.getId());
  }
}
