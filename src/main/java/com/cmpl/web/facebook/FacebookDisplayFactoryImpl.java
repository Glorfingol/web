package com.cmpl.web.facebook;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.BackDisplayFactoryImpl;
import com.cmpl.web.core.model.BaseException;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;

/**
 * Implementation de l'interface de factory pour le spages back de facebook
 * 
 * @author Louis
 *
 */
public class FacebookDisplayFactoryImpl extends BackDisplayFactoryImpl implements FacebookDisplayFactory {

  private final FacebookService facebookService;

  public FacebookDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      FacebookService facebookService) {
    super(menuFactory, messageSource);
    this.facebookService = facebookService;
  }

  @Override
  public ModelAndView computeModelAndViewForFacebookAccessPage(Locale locale) {

    boolean isConnected = isAlreadyConnected();
    if (isConnected) {
      LOGGER.info("Redirection vers l'import car déjà connecté");
      return computeModelAndViewForFacebookImportPage(locale);
    }

    return super.computeModelAndViewForBackPage(BACK_PAGE.FACEBOOK_ACCESS, locale);
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
    return facebookImport;

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
      LOGGER.debug("Utilisateur facebook non connecté", e);
      return false;
    }
    return true;
  }

}
