package com.cmpl.web.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.error.ERROR_CAUSE;
import com.cmpl.web.core.model.Error;
import com.cmpl.web.core.model.ErrorCause;
import com.cmpl.web.core.validator.BaseValidator;
import com.cmpl.web.message.WebMessageSource;

public class PageValidatorImpl extends BaseValidator implements PageValidator {

  public PageValidatorImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(PageCreateForm form, Locale locale) {
    return validate(form.getName(), form.getMenuTitle(), locale);
  }

  @Override
  public Error validateUpdate(PageUpdateForm form, Locale locale) {
    return validate(form.getName(), form.getMenuTitle(), locale);
  }

  Error validate(String name, String menutTitle, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(name)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PAGE_NAME, locale));
    }
    if (!isStringValid(menutTitle)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PAGE_MENU_TITLE, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

}
