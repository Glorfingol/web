package cmpl.web.factory.impl;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.FacebookDisplayFactory;
import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.BaseException;
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
  public ModelAndView computeModelAndViewForFacebookAccessPage(BACK_PAGE backPage, Locale locale) {

    boolean isConnected = isAlreadyConnected();
    if (isConnected) {
      return computeModelAndViewForFacebookImportPage(BACK_PAGE.FACEBOOK_IMPORT, locale);
    }

    ModelAndView facebookAccess = super.computeModelAndViewForBackPage(backPage, locale);

    facebookAccess.addObject("accessTitle", computeAccessTitle(locale));
    facebookAccess.addObject("accessInformation", computeAccessInformation(locale));

    return facebookAccess;
  }

  @Override
  public ModelAndView computeModelAndViewForFacebookImportPage(BACK_PAGE backPage, Locale locale) {

    ModelAndView facebookImport = super.computeModelAndViewForBackPage(backPage, locale);

    return facebookImport;

  }

  String computeAccessTitle(Locale locale) {
    return getI18nValue("access.title", locale);
  }

  String computeAccessInformation(Locale locale) {
    return getI18nValue("access.information", locale);
  }

  boolean isAlreadyConnected() {
    try {
      facebookService.getRecentFeed();
    } catch (BaseException e) {
      return false;
    }
    return true;
  }
}
