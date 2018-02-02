package com.cmpl.web.core.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.validator.BaseValidator;

public class WidgetValidatorImpl extends BaseValidator implements  WidgetValidator {

  public WidgetValidatorImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(WidgetCreateForm form, Locale locale) {
    return validate(form.getName(),form.getType(),locale);
  }

  @Override
  public Error validateUpdate(WidgetUpdateForm form, Locale locale) {
    return validate(form.getName(),form.getType(),locale);
  }


  Error validate(String name, WIDGET_TYPE type, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(name)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PAGE_NAME, locale));
    }
    if (type == null) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PAGE_MENU_TITLE, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }
}
