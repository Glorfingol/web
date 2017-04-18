package cmpl.web.controller.back;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.dispatcher.NewsEntryDispatcher;
import cmpl.web.factory.NewsManagerDisplayFactory;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsEntryResponse;
import cmpl.web.model.page.BACK_PAGE;

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

  @RequestMapping(value = "/manager/news", method = RequestMethod.GET)
  public ModelAndView printViewNews() {

    LOGGER.info("Accès à la page " + BACK_PAGE.NEWS_VIEW.name());
    return newsManagerDisplayFactory.computeModelAndViewForBackPage(BACK_PAGE.NEWS_VIEW, Locale.FRANCE);
  }

  @RequestMapping(value = "/manager/news", method = RequestMethod.POST, produces = "application/json")
  @ResponseBody
  public ResponseEntity<NewsEntryResponse> createNewsEntry(@RequestBody NewsEntryRequest newsEntryRequest) {

    LOGGER.info("Tentative de création d'une entrée de blog");
    try {
      NewsEntryResponse response = dispatcher.createEntity(newsEntryRequest, "fr");
      LOGGER.info("Entrée crée, id " + response.getNewsEntry().getId());
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @RequestMapping(value = "/manager/news/{newsEntryId}", method = RequestMethod.PUT, produces = "application/json")
  @ResponseBody
  public ResponseEntity<NewsEntryResponse> updateNewsEntry(@PathVariable(value = "newsEntryId") String newsEntryId,
      @RequestBody NewsEntryRequest newsEntryRequest) {

    LOGGER.info("Tentative de mise à jour d'une entrée de blog d'id " + newsEntryId);
    try {
      NewsEntryResponse response = dispatcher.updateEntity(newsEntryRequest, newsEntryId, "fr");
      LOGGER.info("Entrée modifiée, id " + newsEntryId);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la mise à jour de l'entrée d'id " + newsEntryId, e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @RequestMapping(value = "/manager/news/{newsEntryId}", method = RequestMethod.DELETE, produces = "application/json")
  @ResponseBody
  public ResponseEntity<NewsEntryResponse> deleteNewsEntry(@PathVariable(value = "newsEntryId") String newsEntryId) {
    LOGGER.info("Tentative de suppréssion d'une entrée, action interdite");
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/manager/news/{newsEntryId}", method = RequestMethod.GET)
  public ModelAndView getNewsEntity(@PathVariable(value = "newsEntryId") String newsEntryId) {

    LOGGER.info("Récupération de l'entrée d'id " + newsEntryId);
    return newsManagerDisplayFactory.computeModelAndViewForOneNewsEntry(BACK_PAGE.NEWS_UPDATE, Locale.FRANCE,
        newsEntryId);
  }
}
