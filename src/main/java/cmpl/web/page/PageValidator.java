package cmpl.web.page;

import java.util.Locale;

import cmpl.web.core.model.Error;

public interface PageValidator {

  Error validateCreate(PageCreateForm form, Locale locale);

  Error validateUpdate(PageUpdateForm form, Locale locale);
}
