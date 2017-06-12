package cmpl.web.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.FacebookDisplayFactory;
import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.BaseException;
import cmpl.web.model.facebook.ImportablePost;
import cmpl.web.model.page.BACK_PAGE;
import cmpl.web.service.FacebookService;

public class FacebookDisplayFactoryImpl extends BackDisplayFactoryImpl implements FacebookDisplayFactory {

  private final FacebookService facebookService;

  protected FacebookDisplayFactoryImpl(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory, FacebookService facebookService) {
    super(menuFactory, footerFactory, messageSource, metaElementFactory);
    this.facebookService = facebookService;
  }

  public static FacebookDisplayFactoryImpl fromFactoriesAndMessageResource(MenuFactory menuFactory,
      FooterFactory footerFactory, WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory,
      FacebookService facebookService) {
    return new FacebookDisplayFactoryImpl(menuFactory, footerFactory, messageSource, metaElementFactory,
        facebookService);
  }

  @Override
  public ModelAndView computeModelAndViewForFacebookAccessPage(Locale locale) {

    boolean isConnected = isAlreadyConnected();
    if (isConnected) {
      LOGGER.info("Redirection vers l'import car déjà connecté");
      return computeModelAndViewForFacebookImportPage(locale);
    }

    ModelAndView facebookAccess = super.computeModelAndViewForBackPage(BACK_PAGE.FACEBOOK_ACCESS, locale);

    facebookAccess.addObject("accessTitle", computeAccessTitle(locale));
    facebookAccess.addObject("accessInformation", computeAccessInformation(locale));

    return facebookAccess;
  }

  @Override
  public ModelAndView computeModelAndViewForFacebookImportPage(Locale locale) {

    ModelAndView facebookImport = super.computeModelAndViewForBackPage(BACK_PAGE.FACEBOOK_IMPORT, locale);

    boolean isConnected = isAlreadyConnected();
    if (!isConnected) {
      LOGGER.info("Redirection vers l'acces car connexion en timeout");
      return computeModelAndViewForFacebookAccessPage(locale);
    }
    facebookImport.addObject("feeds", computeRecentFeeds());
    facebookImport.addObject("importAllLabel", computeImportAll(locale));
    facebookImport.addObject("importOneLabel", computeImportOne(locale));
    return facebookImport;

  }

  String computeAccessTitle(Locale locale) {
    return getI18nValue("access.title", locale);
  }

  String computeAccessInformation(Locale locale) {
    return getI18nValue("access.information", locale);
  }

  String computeImportOne(Locale locale) {
    return getI18nValue("import.one", locale);
  }

  String computeImportAll(Locale locale) {
    return getI18nValue("import.all", locale);
  }

  List<ImportablePost> computeRecentFeeds() {
    try {
      return facebookService.getRecentFeed();
    } catch (BaseException e) {
      LOGGER.error("Impossible de récupérer le feed récent de l'utilisateur", e);
    }
    return new ArrayList<>();
  }

  boolean isAlreadyConnected() {
    try {
      facebookService.getRecentFeed();
    } catch (BaseException e) {
      LOGGER.info("Utilisateur facebook non connecté");
      return false;
    }
    return true;
  }
}
