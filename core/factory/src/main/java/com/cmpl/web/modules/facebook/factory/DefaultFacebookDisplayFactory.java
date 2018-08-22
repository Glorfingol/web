package com.cmpl.web.modules.facebook.factory;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.DefaultBackDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.facebook.FacebookAdapter;
import com.cmpl.web.facebook.ImportablePost;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.servlet.ModelAndView;

/**
 * Implementation de l'interface de factory pour le spages back de facebook
 *
 * @author Louis
 */
public class DefaultFacebookDisplayFactory extends DefaultBackDisplayFactory implements
    FacebookDisplayFactory {

  private final FacebookAdapter facebookAdapter;

  public DefaultFacebookDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      FacebookAdapter facebookAdapter, PluginRegistry<BreadCrumb, String> breadCrumbRegistry,
      Set<Locale> availableLocales, PluginRegistry<BackPage, String> backPagesRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, backPagesRegistry);
    this.facebookAdapter = facebookAdapter;
  }

  @Override
  public ModelAndView computeModelAndViewForFacebookAccessPage(Locale locale) {

    boolean isConnected = isAlreadyConnected();
    if (isConnected) {
      LOGGER.info("Redirection vers l'import car déjà connecté");
      return computeModelAndViewForFacebookImportPage(locale);
    }

    return super
        .computeModelAndViewForBackPage(computeBackPage("FACEBOOK_ACCESS"), locale);
  }

  @Override
  public ModelAndView computeModelAndViewForFacebookImportPage(Locale locale) {

    ModelAndView facebookImport = super
        .computeModelAndViewForBackPage(computeBackPage("FACEBOOK_IMPORT"), locale);

    boolean isConnected = isAlreadyConnected();
    if (!isConnected) {
      LOGGER.info("Redirection vers l'acces car connexion en timeout");
      return computeModelAndViewForFacebookAccessPage(locale);
    }
    facebookImport.addObject("feeds", computeRecentFeeds());
    return facebookImport;

  }

  List<ImportablePost> computeRecentFeeds() {
    return facebookAdapter.getRecentFeed();
  }

  boolean isAlreadyConnected() {
    return facebookAdapter.isAlreadyConnected();
  }

}
