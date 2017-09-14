package cmpl.web.meta;

import java.util.Locale;

import cmpl.web.core.model.Error;

public class MetaElementDispatcherImpl implements MetaElementDispatcher {

  private final MetaElementService metaElementService;
  private final OpenGraphMetaElementService openGraphMetaElementService;
  private final MetaElementTranslator translator;
  private final MetaElementValidator validator;

  public MetaElementDispatcherImpl(MetaElementService metaElementService,
      OpenGraphMetaElementService openGraphMetaElementService, MetaElementTranslator translator,
      MetaElementValidator validator) {
    this.metaElementService = metaElementService;
    this.openGraphMetaElementService = openGraphMetaElementService;
    this.validator = validator;
    this.translator = translator;

  }

  @Override
  public MetaElementResponse createEntity(String pageId, MetaElementCreateForm form, Locale locale) {
    Error error = validator.validateCreate(pageId, form, locale);

    if (error != null) {
      MetaElementResponse response = new MetaElementResponse();
      response.setError(error);
      return response;
    }

    MetaElementDTO metaElementToCreate = translator.fromCreateFormToDTO(pageId, form);
    MetaElementDTO createdMetaElement = metaElementService.createEntity(metaElementToCreate);
    return translator.fromDTOToResponse(createdMetaElement);
  }

  @Override
  public MetaElementResponse createEntity(String pageId, OpenGraphMetaElementCreateForm form, Locale locale) {
    Error error = validator.validateCreate(pageId, form, locale);

    if (error != null) {
      MetaElementResponse response = new MetaElementResponse();
      response.setError(error);
      return response;
    }

    OpenGraphMetaElementDTO metaElementToCreate = translator.fromCreateFormToDTO(pageId, form);
    OpenGraphMetaElementDTO createdMetaElement = openGraphMetaElementService.createEntity(metaElementToCreate);
    return translator.fromDTOToResponse(createdMetaElement);
  }

}