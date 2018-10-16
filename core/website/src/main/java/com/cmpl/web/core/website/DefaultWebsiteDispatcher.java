package com.cmpl.web.core.website;

import com.cmpl.web.core.common.resource.BaseResponse;
import java.util.Locale;
import java.util.Objects;

public class DefaultWebsiteDispatcher implements WebsiteDispatcher {

  private final WebsiteService service;

  private final WebsiteTranslator translator;

  public DefaultWebsiteDispatcher(WebsiteService websiteService,
    WebsiteTranslator websiteTranslator) {
    this.service = Objects.requireNonNull(websiteService);
    this.translator = Objects.requireNonNull(websiteTranslator);
  }

  @Override
  public WebsiteResponse createEntity(WebsiteCreateForm form, Locale locale) {
    WebsiteDTO websiteToCreate = translator.fromCreateFormToDTO(form);
    WebsiteDTO createdWebsite = service.createEntity(websiteToCreate);

    return translator.fromDTOToResponse(createdWebsite);
  }

  @Override
  public WebsiteResponse updateEntity(WebsiteUpdateForm form, Locale locale) {
    WebsiteDTO websiteToUpdate = service.getEntity(form.getId());
    websiteToUpdate.setSecure(form.getSecure());
    websiteToUpdate.setDescription(form.getDescription());
    websiteToUpdate.setName(form.getName());
    websiteToUpdate.setExtension(form.getExtension());
    websiteToUpdate.setSystemJquery(form.getSystemJquery());
    websiteToUpdate.setSystemBootstrap(form.getSystemBootstrap());
    websiteToUpdate.setSystemFontAwesome(form.getSystemFontAwesome());

    WebsiteDTO websiteUpdated = service.updateEntity(websiteToUpdate);

    return translator.fromDTOToResponse(websiteUpdated);
  }

  @Override
  public BaseResponse deleteEntity(String websiteId, Locale locale) {
    service.deleteEntity(Long.parseLong(websiteId));
    return WebsiteResponseBuilder.create().build();
  }
}
