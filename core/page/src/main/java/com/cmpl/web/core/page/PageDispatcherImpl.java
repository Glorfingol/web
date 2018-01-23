package com.cmpl.web.core.page;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;

public class PageDispatcherImpl implements PageDispatcher {

  private final PageValidator validator;
  private final PageTranslator translator;
  private final PageService pageService;

  public PageDispatcherImpl(PageValidator validator, PageTranslator translator, PageService pageService) {
    this.validator = validator;
    this.translator = translator;
    this.pageService = pageService;
  }

  @Override
  public PageResponse createEntity(PageCreateForm form, Locale locale) {

    Error error = validator.validateCreate(form, locale);

    if (error != null) {
      PageResponse response = new PageResponse();
      response.setError(error);
      return response;
    }

    PageDTO pageToCreate = translator.fromCreateFormToDTO(form);
    PageDTO createdPage = pageService.createEntity(pageToCreate);
    return translator.fromDTOToResponse(createdPage);
  }

  @Override
  public PageResponse updateEntity(PageUpdateForm form, Locale locale) {

    Error error = validator.validateUpdate(form, locale);

    if (error != null) {
      PageResponse response = new PageResponse();
      response.setError(error);
      return response;
    }

    PageDTO pageToUpdate = pageService.getEntity(form.getId());
    pageToUpdate.setBody(form.getBody());
    pageToUpdate.setMenuTitle(form.getMenuTitle());
    pageToUpdate.setName(form.getName());
    pageToUpdate.setWithNews(form.isWithNews());

    PageDTO updatedPage = pageService.updateEntity(pageToUpdate);

    return translator.fromDTOToResponse(updatedPage);
  }

}
