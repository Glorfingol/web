package com.cmpl.web.core.website;

public class DefaultWebsiteTranslator implements WebsiteTranslator {

  @Override
  public WebsiteDTO fromCreateFormToDTO(WebsiteCreateForm form) {
    return WebsiteDTOBuilder.create().description(form.getDescription())
      .secure(form.getSecure().booleanValue())
      .systemBootstrap(form.getSystemBootstrap().booleanValue())
      .systemFontAwesome(form.getSystemFontAwesome().booleanValue())
      .systemJquery(form.getSystemJquery().booleanValue())
      .extension(form.getExtension()).name(form.getName()).build();
  }

  @Override
  public WebsiteDTO fromUpdateFormToDTO(WebsiteUpdateForm form) {
    return WebsiteDTOBuilder.create().description(form.getDescription())
      .extension(form.getExtension())
      .secure(form.getSecure().booleanValue())
      .systemBootstrap(form.getSystemBootstrap().booleanValue())
      .systemJquery(form.getSystemJquery().booleanValue())
      .systemFontAwesome(form.getSystemFontAwesome().booleanValue()).name(form.getName())
      .id(form.getId())
      .modificationDate(form.getModificationDate()).modificationUser(form.getModificationUser())
      .creationUser(form.getCreationUser()).creationDate(form.getCreationDate()).build();

  }

  @Override
  public WebsiteResponse fromDTOToResponse(WebsiteDTO dto) {
    return WebsiteResponseBuilder.create().website(dto).build();
  }
}
