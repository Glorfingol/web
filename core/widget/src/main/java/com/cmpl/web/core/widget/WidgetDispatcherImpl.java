package com.cmpl.web.core.widget;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;

public class WidgetDispatcherImpl implements WidgetDispatcher {

  private final WidgetTranslator translator;
  private final WidgetValidator validator;
  private final WidgetService widgetService;

  public WidgetDispatcherImpl(WidgetTranslator translator,WidgetValidator validator,WidgetService widgetService){
    this.translator = translator;
    this.validator = validator;
    this.widgetService = widgetService;

  }

  @Override
  public WidgetResponse createEntity(WidgetCreateForm form, Locale locale) {
    Error error = validator.validateCreate(form, locale);

    if (error != null) {
      return  WidgetResponseBuilder.create().error(error).build();
    }

    WidgetDTO widgetToCreate = translator.fromCreateFormToDTO(form);
    WidgetDTO createdWidget = widgetService.createEntity(widgetToCreate);
    return translator.fromDTOToResponse(createdWidget);
  }

  @Override
  public WidgetResponse updateEntity(WidgetUpdateForm form, Locale locale) {

    Error error = validator.validateUpdate(form, locale);

    if (error != null) {
      return  WidgetResponseBuilder.create().error(error).build();
    }
    
    WidgetDTO widgetToUpdate = widgetService.getEntity(form.getId());
    widgetToUpdate.setName(form.getName());
    widgetToUpdate.setPersonalization(form.getPersonalization());
    widgetToUpdate.setType(form.getType());


    WidgetDTO updatedWidget = widgetService.updateEntity(widgetToUpdate);

    return translator.fromDTOToResponse(updatedWidget);
  }
}
