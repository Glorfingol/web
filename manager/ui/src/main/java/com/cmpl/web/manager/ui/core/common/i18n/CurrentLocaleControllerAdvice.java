package com.cmpl.web.manager.ui.core.common.i18n;

import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;
import java.util.Locale;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = ManagerController.class)
public class CurrentLocaleControllerAdvice {

  @ModelAttribute("currentLocale")
  public Locale getCurrentLocale(Locale locale) {
    return locale;
  }

}
