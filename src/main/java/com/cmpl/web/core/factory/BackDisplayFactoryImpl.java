package com.cmpl.web.core.factory;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.menu.MenuItem;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;

/**
 * Implementation de l'interface commune de factory pour le back
 * 
 * @author Louis
 *
 */
public class BackDisplayFactoryImpl extends BaseDisplayFactoryImpl implements BackDisplayFactory {

  protected static final Logger LOGGER = LoggerFactory.getLogger(BackDisplayFactoryImpl.class);

  private final MenuFactory menuFactory;
  private final PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;

  public BackDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry) {
    super(messageSource);
    this.menuFactory = menuFactory;
    this.breadCrumbRegistry = breadCrumbRegistry;
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, Locale locale) {

    LOGGER.info("Construction de la page du back " + backPage.name());
    ModelAndView model = computeModelAndViewBaseTile(backPage, locale);

    LOGGER.info("Construction du menu pour la page " + backPage.name());
    model.addObject("menuItems", computeBackMenuItems(backPage, locale));
    LOGGER.info("Construction du fil d'ariane pour la page " + backPage.name());
    model.addObject("breadcrumb", computeBreadCrumb(backPage));
    LOGGER.info("Construction du lien du back pour la page " + backPage.name());
    model.addObject("hiddenLink", computeHiddenLink(locale));

    LOGGER.info("Page du back " + backPage.name() + " prête");

    return model;
  }

  public BreadCrumb computeBreadCrumb(BACK_PAGE backPage) {
    BreadCrumb breadCrumbFromRegistry = breadCrumbRegistry.getPluginFor(backPage);
    if (breadCrumbFromRegistry == null) {
      return null;
    }
    return BreadCrumbBuilder.create().items(breadCrumbFromRegistry.getItems()).page(breadCrumbFromRegistry.getPage())
        .build();
  }

  ModelAndView computeModelAndViewBaseTile(BACK_PAGE backPage, Locale locale) {

    if (BACK_PAGE.LOGIN.equals(backPage)) {
      return new ModelAndView(computeTileName(backPage.getTile(), locale));
    }

    ModelAndView model = new ModelAndView(computeDecoratorBackTileName(locale));
    model.addObject("content", computeTileName(backPage.getTile(), locale));
    return model;

  }

  public List<MenuItem> computeBackMenuItems(BACK_PAGE backPage, Locale locale) {
    return menuFactory.computeBackMenuItems(backPage, locale);
  }

}
