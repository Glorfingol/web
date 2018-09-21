package com.cmpl.web.core.factory;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.page.BackPage;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.servlet.ModelAndView;

/**
 * Implementation de l'interface commune de factory pour le back
 *
 * @author Louis
 */
public class DefaultBackDisplayFactory extends DefaultBaseDisplayFactory implements
    BackDisplayFactory {

  protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultBackDisplayFactory.class);

  private final MenuFactory menuFactory;

  private final PluginRegistry<BreadCrumb, String> breadCrumbRegistry;

  protected final PluginRegistry<BackPage, String> backPagesRegistry;

  protected final Set<Locale> availableLocales;


  public DefaultBackDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, String> breadCrumbRegistry, Set<Locale> availableLocales,
      PluginRegistry<BackPage, String> backPagesRegistry) {
    super(messageSource);

    this.menuFactory = Objects.requireNonNull(menuFactory);

    this.breadCrumbRegistry = Objects.requireNonNull(breadCrumbRegistry);

    this.availableLocales = Objects.requireNonNull(availableLocales);

    this.backPagesRegistry = Objects.requireNonNull(backPagesRegistry);


  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(BackPage backPage,
      Locale locale) {

    String backPageName = backPage.getPageName();
    LOGGER.debug("Construction de la page du back {}", backPageName);
    ModelAndView model = computeModelAndViewBaseTile(backPage);

    LOGGER.debug("Construction du menu pour la page {}", backPageName);
    model.addObject("menuItems", computeBackMenuItems(backPage, locale));
    LOGGER.debug("Construction des locales pour la page {}", backPageName);
    model.addObject("locales", availableLocales);
    LOGGER.debug("Construction du fil d'ariane pour la page {}", backPageName);
    model.addObject("breadcrumb", computeBreadCrumb(backPage));

    LOGGER.debug("Page du back {} prête", backPageName);

    return model;
  }


  public BreadCrumb computeBreadCrumb(BackPage backPage) {
    BreadCrumb breadCrumbFromRegistry = breadCrumbRegistry.getPluginFor(backPage.getPageName());
    if (breadCrumbFromRegistry == null) {
      return null;
    }
    return BreadCrumbBuilder.create().items(breadCrumbFromRegistry.getItems())
        .pageName(breadCrumbFromRegistry.getPageName())
        .build();
  }

  public ModelAndView computeModelAndViewBaseTile(BackPage backPage) {

    if (!backPage.isDecorated()) {
      return new ModelAndView(backPage.getTemplatePath());
    }

    ModelAndView model = new ModelAndView("decorator_back");
    model.addObject("content", backPage.getTemplatePath());
    return model;

  }

  public List<MenuItem> computeBackMenuItems(BackPage backPage, Locale locale) {
    return menuFactory.computeBackMenuItems(backPage, locale);
  }

  public BackPage computeBackPage(String pageName) {
    return backPagesRegistry.getPluginFor(pageName);
  }

}
