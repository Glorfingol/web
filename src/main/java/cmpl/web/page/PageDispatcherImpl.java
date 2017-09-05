package cmpl.web.page;

import java.util.Locale;

import cmpl.web.core.model.Error;

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

}
