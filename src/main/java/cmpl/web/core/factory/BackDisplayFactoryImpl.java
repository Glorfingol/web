package cmpl.web.core.factory;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.footer.Footer;
import cmpl.web.footer.FooterFactory;
import cmpl.web.login.LoginFormDisplayBean;
import cmpl.web.menu.MenuFactory;
import cmpl.web.menu.MenuItem;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.meta.MetaElementToDelete;
import cmpl.web.page.BACK_PAGE;

/**
 * Implementation de l'interface commune de factory pour le back
 * 
 * @author Louis
 *
 */
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

  /**
   * Constructeur static pour la configuration
   * 
   * @param menuFactory
   * @param footerFactory
   * @param messageSource
   * @param metaElementFactory
   * @return
   */
  public static BackDisplayFactoryImpl fromFactoriesAndMessageResource(MenuFactory menuFactory,
      FooterFactory footerFactory, WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory) {
    return new BackDisplayFactoryImpl(menuFactory, footerFactory, messageSource, metaElementFactory);
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, Locale locale) {

    LOGGER.info("Construction de la page du back " + backPage.name());
    ModelAndView model = computeModelAndViewBaseTile(backPage, locale);

    LOGGER.info("Construction du menu pour la page " + backPage.name());
    model.addObject("menuItems", computeBackMenuItems(backPage, locale));
    LOGGER.info("Construction du footer pour la page " + backPage.name());
    model.addObject("footer", computeFooter(locale));
    LOGGER.info("Construction du titre principal pour la page " + backPage.name());
    model.addObject("mainTitle", computeMainTitle(locale));
    LOGGER.info("Construction des éléments meta pour la page " + backPage.name());
    model.addObject("metaItems", computeMetaElements(locale));
    LOGGER.info("Construction du formulaire de login pour la page " + backPage.name());
    model.addObject("loginForm", computeLoginFormDisplayBean(locale));
    LOGGER.info("Construction du lien du back pour la page " + backPage.name());
    model.addObject("hiddenLink", computeHiddenLink(locale));

    LOGGER.info("Page du back " + backPage.name() + " prête");

    return model;
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

  public Footer computeFooter(Locale locale) {
    return footerFactory.computeFooter(locale);
  }

  public List<MetaElementToDelete> computeMetaElements(Locale locale) {
    return metaElementFactory.computeMetaElementsForBackPage(locale);
  }

  public LoginFormDisplayBean computeLoginFormDisplayBean(Locale locale) {
    LoginFormDisplayBean loginFormDisplayBean = new LoginFormDisplayBean();

    loginFormDisplayBean.setUserLabel(getI18nValue("user.name", locale));
    loginFormDisplayBean.setPasswordLabel(getI18nValue("user.password", locale));
    loginFormDisplayBean.setErrorLabel(getI18nValue("user.error", locale));
    loginFormDisplayBean.setTimeoutLabel(getI18nValue("user.logout", locale));

    return loginFormDisplayBean;
  }

}
