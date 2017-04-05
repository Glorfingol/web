package cmpl.web.controller.back;

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

  private final NewsManagerDisplayFactory newsManagerDisplayFactory;
  private final NewsEntryDispatcher dispatcher;

  @Autowired
  NewsManagerController(NewsManagerDisplayFactory newsManagerDisplayFactory, NewsEntryDispatcher dispatcher) {
    this.newsManagerDisplayFactory = newsManagerDisplayFactory;
    this.dispatcher = dispatcher;
  }

  @RequestMapping(value = "/manager/news", method = RequestMethod.GET)
  public ModelAndView printViewNews() {

    return newsManagerDisplayFactory.computeModelAndViewForBackPage(BACK_PAGE.NEWS_VIEW, "fr");
  }

  @RequestMapping(value = "/manager/news", method = RequestMethod.POST, produces = "application/json")
  @ResponseBody
  public ResponseEntity<NewsEntryResponse> createNewsEntry(@RequestBody NewsEntryRequest newsEntryRequest) {

    try {
      NewsEntryResponse response = dispatcher.createEntity(newsEntryRequest, "fr");
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @RequestMapping(value = "/manager/news/{newsEntryId}", method = RequestMethod.PUT, produces = "application/json")
  @ResponseBody
  public ResponseEntity<NewsEntryResponse> updateNewsEntry(@PathVariable(value = "newsEntryId") String newsEntryId,
      @RequestBody NewsEntryRequest newsEntryRequest) {

    try {
      NewsEntryResponse response = dispatcher.updateEntity(newsEntryRequest, newsEntryId, "fr");
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @RequestMapping(value = "/manager/news/{newsEntryId}", method = RequestMethod.DELETE, produces = "application/json")
  @ResponseBody
  public ResponseEntity<NewsEntryResponse> deleteNewsEntry(@PathVariable(value = "newsEntryId") String newsEntryId) {

    try {
      dispatcher.deleteEntity(newsEntryId, "fr");
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @RequestMapping(value = "/manager/news/{newsEntryId}", method = RequestMethod.GET)
  public ModelAndView getNewsEntity(@PathVariable(value = "newsEntryId") String newsEntryId) {

    return newsManagerDisplayFactory.computeModelAndViewForOneNewsEntry(BACK_PAGE.NEWS_UPDATE, "fr", newsEntryId);
  }
}
