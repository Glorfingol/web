package cmpl.web.meta;

import java.util.Locale;

public interface MetaElementDispatcher {

  MetaElementResponse createEntity(String pageId, MetaElementCreateForm form, Locale locale);

  MetaElementResponse createEntity(String pageId, OpenGraphMetaElementCreateForm form, Locale locale);
}
