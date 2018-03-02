package com.cmpl.web.manager.ui.modules.facebook;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.facebook.FacebookDispatcher;
import com.cmpl.web.facebook.FacebookImportRequest;
import com.cmpl.web.facebook.FacebookImportResponse;
import com.cmpl.web.manager.ui.core.stereotype.ManagerController;
import com.cmpl.web.modules.facebook.factory.FacebookDisplayFactory;

/**
 * Controller pour la gestion de l'import des posts facebook
 * 
 * @author Louis
 *
 */
@ManagerController
public class FacebookController {

  private static final Logger LOGGER = LoggerFactory.getLogger(FacebookController.class);

  private final FacebookDisplayFactory facebookDisplayFactory;
  private final FacebookDispatcher facebookDispatcher;

  public FacebookController(FacebookDisplayFactory facebookDisplayFactory, FacebookDispatcher facebookDispatcher) {
    this.facebookDisplayFactory = facebookDisplayFactory;
    this.facebookDispatcher = facebookDispatcher;
  }

  /**
   * Mapping pour acceder a la partie facebook
   * 
   * @return
   */
  @GetMapping(value = "/manager/facebook")
  @PreAuthorize("hasAuthority('webmastering:facebook:import')")
  public ModelAndView printFacebookAccess(Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.FACEBOOK_ACCESS.name());
    return facebookDisplayFactory.computeModelAndViewForFacebookAccessPage(locale);
  }

  /**
   * Mapping pour l'affichage des imports possibles
   * 
   * @return
   */
  @GetMapping(value = "/manager/facebook/import")
  @PreAuthorize("hasAuthority('webmastering:facebook:import')")
  public ModelAndView printFacebookImport(Locale locale) {

    LOGGER.info("Accès à la page " + BACK_PAGE.FACEBOOK_IMPORT.name());
    return facebookDisplayFactory.computeModelAndViewForFacebookImportPage(locale);
  }

  /**
   * Mapping pour l'import de posts
   * 
   * @param facebookImportRequest
   * @return
   */
  @PostMapping(value = "/manager/facebook/import")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:facebook:import')")
  public ResponseEntity<FacebookImportResponse> createNewsEntry(
      @RequestBody FacebookImportRequest facebookImportRequest, Locale locale) {

    LOGGER.info("Tentative de création d'entrées de blog venant de facebook");
    try {
      FacebookImportResponse response = facebookDispatcher.createEntity(facebookImportRequest, locale);
      LOGGER.info("Entrées crées");
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (BaseException e) {
      LOGGER.info("Echec de l'import des posts facebook", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }
}
