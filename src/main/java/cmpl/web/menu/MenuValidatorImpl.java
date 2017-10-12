package cmpl.web.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import cmpl.web.core.error.ERROR_CAUSE;
import cmpl.web.core.model.Error;
import cmpl.web.core.model.ErrorCause;
import cmpl.web.core.validator.BaseValidator;
import cmpl.web.message.WebMessageSourceImpl;

public class MenuValidatorImpl extends BaseValidator implements MenuValidator {

  public MenuValidatorImpl(WebMessageSourceImpl messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(MenuCreateForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();

    if (!isStringValid(form.getTitle())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_MENU_TITLE, locale));
    }

    if (!isStringValid(form.getPageId())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PAGE, locale));
    }

    if (0 == form.getOrderInMenu()) {
      causes.add(computeCause(ERROR_CAUSE.BAD_ORDER, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateUpdate(MenuUpdateForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();

    if (!isStringValid(form.getTitle())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_MENU_TITLE, locale));
    }

    if (!isStringValid(form.getPageId())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PAGE, locale));
    }

    if (0 == form.getOrderInMenu()) {
      causes.add(computeCause(ERROR_CAUSE.BAD_ORDER, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

}
