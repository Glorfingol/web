package cmpl.web.factory.impl;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.BackDisplayFactory;
import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.footer.Footer;
import cmpl.web.model.login.LoginFormDisplayBean;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.page.BACK_PAGE;

public class BackDisplayFactoryImpl extends BaseDisplayFactoryImpl implements BackDisplayFactory {

  protected static final Logger LOGGER = LoggerFactory.getLogger(BackDisplayFactoryImpl.class);

  private final MenuFactory menuFactory;
  private final FooterFactory footerFactory;
  private final MetaElementFactory metaElementFactory;

  protected BackDisplayFactoryImpl(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory) {
    super(messageSource);
    this.menuFactory = menuFactory;
    this.footerFactory = footerFactory;
    this.metaElementFactory = metaElementFactory;
  }

  public static BackDisplayFactoryImpl fromFactoriesAndMessageResource(MenuFactory menuFactory,
      FooterFactory footerFactory, WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory) {
    return new BackDisplayFactoryImpl(menuFactory, footerFactory, messageSource, metaElementFactory);
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, Locale locale) {

    LOGGER.info("Construction de la page du back " + backPage.name());
    ModelAndView model = new ModelAndView(computeTileName(backPage.getTile(), locale));

    LOGGER.info("Construction du menu pour la page " + backPage.name());
    model.addObject("menuItems", computeBackMenuItems(backPage, locale));
    LOGGER.info("Construction du footer pour la page " + backPage.name());
    model.addObject("footer", computeFooter(locale));
    LOGGER.info("Construction du titre principal pour la page " + backPage.name());
    model.addObject("maintTitle", computeMainTitle(locale));
    LOGGER.info("Construction des éléments meta pour la page " + backPage.name());
    model.addObject("metaItems", computeMetaElements(locale));
    LOGGER.info("Construction du formulaire de login pour la page " + backPage.name());
    model.addObject("loginForm", computeLoginFormDisplayBean(locale));
    LOGGER.info("Construction du lien du back pour la page " + backPage.name());
    model.addObject("hiddenLink", computeHiddenLink(locale));

    LOGGER.info("Page du back " + backPage.name() + " prête");

    return model;
  }

  List<MenuItem> computeBackMenuItems(BACK_PAGE backPage, Locale locale) {
    return menuFactory.computeBackMenuItems(backPage, locale);
  }

  Footer computeFooter(Locale locale) {
    return footerFactory.computeFooter(locale);
  }

  List<MetaElement> computeMetaElements(Locale locale) {
    return metaElementFactory.computeMetaElementsForBackPage(locale);
  }

  LoginFormDisplayBean computeLoginFormDisplayBean(Locale locale) {
    LoginFormDisplayBean loginFormDisplayBean = new LoginFormDisplayBean();

    loginFormDisplayBean.setUserLabel(getI18nValue("user.name", locale));
    loginFormDisplayBean.setPasswordLabel(getI18nValue("user.password", locale));
    loginFormDisplayBean.setErrorLabel(getI18nValue("user.error", locale));
    loginFormDisplayBean.setTimeoutLabel(getI18nValue("user.logout", locale));

    return loginFormDisplayBean;
  }

}
