package com.cmpl.web.core.factory.index;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.DefaultBackDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.page.BackPage;
import java.util.Locale;
import java.util.Set;
import org.springframework.plugin.core.PluginRegistry;

public class DefaultIndexDisplayFactory extends DefaultBackDisplayFactory implements
    IndexDisplayFactory {

  public DefaultIndexDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, String> breadCrumbRegistry, Set<Locale> availableLocales,
      PluginRegistry<BackPage, String> backPagesRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales,
        backPagesRegistry);
  }
}
