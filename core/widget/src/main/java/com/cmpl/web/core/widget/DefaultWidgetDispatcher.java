package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.widget.page.WidgetPageCreateForm;
import com.cmpl.web.core.widget.page.WidgetPageDTO;
import com.cmpl.web.core.widget.page.WidgetPageResponse;
import com.cmpl.web.core.widget.page.WidgetPageService;
import java.util.Locale;
import java.util.Objects;

public class DefaultWidgetDispatcher implements WidgetDispatcher {

  private final WidgetTranslator translator;

  private final WidgetService widgetService;

  private final WidgetPageService widgetPageService;


  public DefaultWidgetDispatcher(WidgetTranslator translator, WidgetService widgetService,
    WidgetPageService widgetPageService) {
    this.translator = Objects.requireNonNull(translator);
    this.widgetService = Objects.requireNonNull(widgetService);
    this.widgetPageService = Objects.requireNonNull(widgetPageService);

  }

  @Override
  public WidgetResponse createEntity(WidgetCreateForm form, String personalization) {

    WidgetDTO widgetToCreate = translator.fromCreateFormToDTO(form);
    widgetToCreate.setPersonalization(personalization);
    String widgetName = form.getName().replace("-", "_");
    widgetToCreate.setName(widgetName);
    WidgetDTO createdWidget = widgetService.createEntity(widgetToCreate, form.getLocaleCode());
    return translator.fromDTOToResponse(createdWidget);
  }

  @Override
  public WidgetResponse updateEntity(WidgetUpdateForm form, Locale locale) {

    WidgetDTO widgetToUpdate = widgetService.getEntity(form.getId());
    String widgetName = form.getName().replace("-", "_");
    widgetToUpdate.setName(widgetName);
    widgetToUpdate.setPersonalization(form.getPersonalization());
    widgetToUpdate.setType(form.getType());
    widgetToUpdate.setEntityId(form.getEntityId());
    widgetToUpdate.setAsynchronous(form.getAsynchronous().booleanValue());

    WidgetDTO updatedWidget = widgetService.updateEntity(widgetToUpdate, form.getLocaleCode());

    return translator.fromDTOToResponse(updatedWidget);
  }

  @Override
  public WidgetResponse deleteEntity(String widgetId, Locale locale) {
    widgetService.deleteEntity(Long.parseLong(widgetId));
    return WidgetResponseBuilder.create().build();
  }

  @Override
  public WidgetPageResponse createEntity(String pageId, WidgetPageCreateForm form, Locale locale) {

    WidgetPageDTO widgetPageToCreateToCreate = translator.fromCreateFormToDTO(form);
    WidgetPageDTO createdWidgetPageToCreate = widgetPageService
      .createEntity(widgetPageToCreateToCreate);
    return translator.fromDTOToResponse(createdWidgetPageToCreate);
  }

  @Override
  public void deleteEntity(String pageId, String widgetId, Locale locale) throws BaseException {

    WidgetPageDTO widgetPageDTO = widgetPageService
      .findByPageIdAndWidgetId(Long.parseLong(pageId), Long.parseLong(widgetId));
    widgetPageService.deleteEntity(widgetPageDTO.getId());

  }


}
