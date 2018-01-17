package com.cmpl.web.page;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller pour la gestion des pages dans le back office
 * 
 * @author Louis
 *
 */
@Controller
@RequestMapping(value = "/manager/pages")
public class PageManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PageManagerController.class);

  private final PageManagerDisplayFactory pageManagerDisplayFactory;
  private final PageDispatcher pageDispatcher;

  @Autowired
  public PageManagerController(PageManagerDisplayFactory pageManagerDisplayFactory, PageDispatcher pageDispatcher) {
    this.pageManagerDisplayFactory = pageManagerDisplayFactory;
    this.pageDispatcher = pageDispatcher;
  }

  /**
   * Mapping pour la page d'affichage de toute les NewsEntry
   * 
   * @return
   */
  @GetMapping
  public ModelAndView printViewPages(@RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_VIEW.name());
    return pageManagerDisplayFactory.computeModelAndViewForViewAllPages(locale, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  public ModelAndView printCreatePage(Locale locale) {
    LOGGER.info("Accès à la page de création des pages");
    return pageManagerDisplayFactory.computeModelAndViewForCreatePage(locale);
  }

  @PostMapping
  @ResponseBody
  public ResponseEntity<PageResponse> createPage(@RequestBody PageCreateForm createForm, Locale locale) {

    LOGGER.info("Tentative de création d'une page");
    try {
      PageResponse response = pageDispatcher.createEntity(createForm, locale);
      if (response.getPage() != null) {
        LOGGER.info("Entrée crée, id " + response.getPage().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @PutMapping(value = "/{pageId}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<PageResponse> updatePage(@RequestBody PageUpdateForm updateForm, Locale locale) {

    LOGGER.info("Tentative de modification d'une page");
    try {
      PageResponse response = pageDispatcher.updateEntity(updateForm, locale);
      if (response.getPage() != null) {
        LOGGER.info("Entrée modifiée, id " + response.getPage().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @GetMapping(value = "/{pageId}")
  public ModelAndView printViewUpdatePage(@PathVariable(value = "pageId") String pageId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId);
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePage(locale, pageId);
  }

  @GetMapping(value = "/{pageId}/_main")
  public ModelAndView printViewUpdatePageMain(@PathVariable(value = "pageId") String pageId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie main");
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePageMain(locale, pageId);
  }

  @GetMapping(value = "/{pageId}/_body")
  public ModelAndView printViewUpdatePageBody(@PathVariable(value = "pageId") String pageId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie body");
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePageBody(locale, pageId);
  }

  @GetMapping(value = "/{pageId}/_header")
  public ModelAndView printViewUpdatePageHeader(@PathVariable(value = "pageId") String pageId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie header");
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePageHeader(locale, pageId);
  }

  @GetMapping(value = "/{pageId}/_footer")
  public ModelAndView printViewUpdatePageFooter(@PathVariable(value = "pageId") String pageId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie footer");
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePageFooter(locale, pageId);
  }

  @GetMapping(value = "/{pageId}/_meta")
  public ModelAndView printViewUpdatePageMeta(@PathVariable(value = "pageId") String pageId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie meta");
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePageMeta(locale, pageId);
  }

  @GetMapping(value = "/{pageId}/_open_graph_meta")
  public ModelAndView printViewUpdatePageOpenGraphMeta(@PathVariable(value = "pageId") String pageId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie meta");
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePageOpenGraphMeta(locale, pageId);
  }

}
