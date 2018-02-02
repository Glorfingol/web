package com.cmpl.web.core.widget;

public class WidgetTranslatorImpl implements WidgetTranslator {

  @Override
  public WidgetDTO fromCreateFormToDTO(WidgetCreateForm form) {
    return WidgetDTOBuilder.create().name(form.getName()).type(form.getType()).build();
  }

  @Override
  public WidgetResponse fromDTOToResponse(WidgetDTO dto) {
    return WidgetResponseBuilder.create().widget(dto).build();
  }
}
