package cmpl.web.meta;

import java.util.Locale;

import cmpl.web.core.model.Error;

public interface MetaElementValidator {

  Error validateCreate(String pageId, MetaElementCreateForm form, Locale locale);

  Error validateCreate(String pageId, OpenGraphMetaElementCreateForm form, Locale locale);

  Error validateDelete(String id, Locale locale);

}
