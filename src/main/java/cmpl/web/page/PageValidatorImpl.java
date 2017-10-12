package cmpl.web.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import cmpl.web.core.error.ERROR_CAUSE;
import cmpl.web.core.model.Error;
import cmpl.web.core.model.ErrorCause;
import cmpl.web.core.validator.BaseValidator;
import cmpl.web.message.WebMessageSourceImpl;

public class PageValidatorImpl extends BaseValidator implements PageValidator {

  public PageValidatorImpl(WebMessageSourceImpl messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(PageCreateForm form, Locale locale) {

    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(form.getName())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PAGE_NAME, locale));
    }
    if (!isStringValid(form.getMenuTitle())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PAGE_MENU_TITLE, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateUpdate(PageUpdateForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(form.getName())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PAGE_NAME, locale));
    }
    if (!isStringValid(form.getMenuTitle())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PAGE_MENU_TITLE, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

}
