package com.cmpl.web.news;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.page.BACK_PAGE;

/**
 * Controller pour la gestion des NewsEntry dans le back office
 * 
 * @author Louis
 *
 */
@Controller
public class NewsManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(NewsManagerController.class);

  private final NewsManagerDisplayFactory newsManagerDisplayFactory;
  private final NewsEntryDispatcher dispatcher;

  @Autowired
  NewsManagerController(NewsManagerDisplayFactory newsManagerDisplayFactory, NewsEntryDispatcher dispatcher) {
    this.newsManagerDisplayFactory = newsManagerDisplayFactory;
    this.dispatcher = dispatcher;
  }

  /**
   * Mapping pour la page d'affichage de toute les NewsEntry
   * 
   * @return
   */
  @GetMapping(value = "/manager/news")
  public ModelAndView printViewNews(@RequestParam(name = "p", required = false) Integer pageNumber) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.NEWS_VIEW.name());
    return newsManagerDisplayFactory
        .computeModelAndViewForBackPage(BACK_PAGE.NEWS_VIEW, Locale.FRANCE, pageNumberToUse);
  }

  /**
   * Mapping pour la creation d'une NewsEntry
   * 
   * @return
   */
  @GetMapping(value = "/manager/news/_create")
  public ModelAndView printCreateNews() {

    LOGGER.info("Accès à la page " + BACK_PAGE.NEWS_VIEW.name());
    return newsManagerDisplayFactory.computeModelAndViewForBackPageCreateNews(BACK_PAGE.NEWS_CREATE, Locale.FRANCE);
  }

  /**
   * Mapping pour la creation d'une NewsEntry
   * 
   * @param newsEntryRequest
   * @return
   */
  @PostMapping(value = "/manager/news", produces = "application/json")
  @ResponseBody
  public ResponseEntity<NewsEntryResponse> createNewsEntry(@RequestBody NewsEntryRequest newsEntryRequest) {

    LOGGER.info("Tentative de création d'une entrée de blog");
    try {
      NewsEntryResponse response = dispatcher.createEntity(newsEntryRequest, Locale.FRANCE);
      if (response.getNewsEntry() != null) {
        LOGGER.info("Entrée crée, id " + response.getNewsEntry().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  /**
   * Mapping pour la modification d'une NewsEntry
   * 
   * @param newsEntryId
   * @param newsEntryRequest
   * @return
   */
  @PutMapping(value = "/manager/news/{newsEntryId}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<NewsEntryResponse> updateNewsEntry(@PathVariable(value = "newsEntryId") String newsEntryId,
      @RequestBody NewsEntryRequest newsEntryRequest) {

    LOGGER.info("Tentative de mise à jour d'une entrée de blog d'id " + newsEntryId);
    try {
      NewsEntryResponse response = dispatcher.updateEntity(newsEntryRequest, newsEntryId, Locale.FRANCE);
      LOGGER.info("Entrée modifiée, id " + newsEntryId);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la mise à jour de l'entrée d'id " + newsEntryId, e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  /**
   * Mapping pour la suppression d'une NewsEntry
   * 
   * @param newsEntryId
   * @return
   */
  @DeleteMapping(value = "/manager/news/{newsEntryId}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<NewsEntryResponse> deleteNewsEntry(@PathVariable(value = "newsEntryId") String newsEntryId) {
    LOGGER.info("Tentative de suppression d'une entrée d'id " + newsEntryId + ", action interdite");
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  /**
   * Mapping pour la recuperation d'une NewsEntry
   * 
   * @param newsEntryId
   * @return
   */
  @GetMapping(value = "/manager/news/{newsEntryId}")
  public ModelAndView getNewsEntity(@PathVariable(value = "newsEntryId") String newsEntryId) {

    LOGGER.info("Récupération de l'entrée d'id " + newsEntryId);
    return newsManagerDisplayFactory.computeModelAndViewForOneNewsEntry(BACK_PAGE.NEWS_UPDATE, Locale.FRANCE,
        newsEntryId);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }
}
