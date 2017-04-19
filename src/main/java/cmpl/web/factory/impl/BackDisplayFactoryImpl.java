package cmpl.web.factory.impl;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.builder.FooterBuilder;
import cmpl.web.builder.MenuBuilder;
import cmpl.web.builder.MetaElementBuilder;
import cmpl.web.factory.BackDisplayFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.footer.Footer;
import cmpl.web.model.login.LoginFormDisplayBean;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.page.BACK_PAGE;

public class BackDisplayFactoryImpl implements BackDisplayFactory {

  protected static final Logger LOGGER = LoggerFactory.getLogger(BackDisplayFactoryImpl.class);

  private final WebMessageSourceImpl messageSource;
  private final MenuBuilder menuBuilder;
  private final FooterBuilder footerBuilder;
  private final MetaElementBuilder metaElementBuilder;

  protected BackDisplayFactoryImpl(MenuBuilder menuBuilder, FooterBuilder footerBuilder,
      WebMessageSourceImpl messageSource, MetaElementBuilder metaElementBuilder) {
    this.menuBuilder = menuBuilder;
    this.messageSource = messageSource;
    this.footerBuilder = footerBuilder;
    this.metaElementBuilder = metaElementBuilder;
  }

  public static BackDisplayFactoryImpl fromBuilders(MenuBuilder menuBuilder, FooterBuilder footerBuilder,
      WebMessageSourceImpl messageSource, MetaElementBuilder metaElementBuilder) {
    return new BackDisplayFactoryImpl(menuBuilder, footerBuilder, messageSource, metaElementBuilder);
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, Locale locale) {

    LOGGER.info("Construction de la page du back " + backPage.name());
    ModelAndView model = new ModelAndView(computeI18nLabel(backPage.getTile(), locale));

    LOGGER.info("Construction du menu pour la page " + backPage.name());
    model.addObject("menuItems", computeBackMenuItems(locale));
    LOGGER.info("Construction du footer pour la page " + backPage.name());
    model.addObject("footer", computeFooter(locale));
    LOGGER.info("Construction du titre principal pour la page " + backPage.name());
    model.addObject("maintTitle", computeMainTitle(locale));
    LOGGER.info("Construction des éléments meta pour la page " + backPage.name());
    model.addObject("metaItems", computeMetaElements(locale));
    LOGGER.info("Construction du formulaire de login pour la page " + backPage.name());
    model.addObject("loginForm", computeLoginFormDisplayBean(locale));
    LOGGER.info("Construction du lien du back pour la page " + backPage.name());
    model.addObject("hiddenLink", computeI18nLabel("back.news.href", locale));

    LOGGER.info("Page du back " + backPage.name() + " prête");

    return model;
  }

  String computeMainTitle(Locale locale) {
    return computeI18nLabel("main.title", locale);
  }

  List<MenuItem> computeBackMenuItems(Locale locale) {
    return menuBuilder.computeBackMenuItems(locale);
  }

  Footer computeFooter(Locale locale) {
    return footerBuilder.computeFooter(locale);
  }

  String computeI18nLabel(String key, Locale locale) {
    return messageSource.getMessage(key, null, locale);
  }

  List<MetaElement> computeMetaElements(Locale locale) {
    return metaElementBuilder.computeMetaElementsForBackPage(locale);
  }

  LoginFormDisplayBean computeLoginFormDisplayBean(Locale locale) {
    LoginFormDisplayBean loginFormDisplayBean = new LoginFormDisplayBean();

    loginFormDisplayBean.setUserLabel(computeI18nLabel("user.name", locale));
    loginFormDisplayBean.setPasswordLabel(computeI18nLabel("user.password", locale));
    loginFormDisplayBean.setErrorLabel(computeI18nLabel("user.error", locale));
    loginFormDisplayBean.setTimeoutLabel(computeI18nLabel("user.logout", locale));

    return loginFormDisplayBean;
  }

}
