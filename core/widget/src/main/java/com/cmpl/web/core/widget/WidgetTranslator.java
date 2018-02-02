package com.cmpl.web.core.widget;

public interface WidgetTranslator {


  WidgetDTO fromCreateFormToDTO(WidgetCreateForm form);

  WidgetResponse fromDTOToResponse(WidgetDTO dto);

}
