package cmpl.web.news;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.page.PAGES;

/**
 * Controller sur les NewsEntry
 * 
 * @author Louis
 *
 */
@Controller
public class NewsController {

  private static final Logger LOGGER = LoggerFactory.getLogger(NewsController.class);
  private final NewsDisplayFactory newsDisplayFactory;

  @Autowired
  NewsController(NewsDisplayFactory newsDisplayFactory) {
    this.newsDisplayFactory = newsDisplayFactory;
  }

  /**
   * Mapping pour afficher une page de newsEntry
   * 
   * @param pageNumber le numero de la page a afficher
   * @return
   */
  @GetMapping(value = "/actualites")
  public ModelAndView printNews(@RequestParam(name = "p", required = false) Integer pageNumber) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + PAGES.NEWS.name());
    return newsDisplayFactory.computeModelAndViewForPage(PAGES.NEWS, Locale.FRANCE, pageNumberToUse);
  }

  /**
   * Mapping pour afficher une NewsEntry
   * 
   * @param newsEntryId
   * @return
   */
  @GetMapping(value = "/actualites/{newsEntryId}")
  public ModelAndView printNewsEntry(@PathVariable(value = "newsEntryId") String newsEntryId) {

    LOGGER.info("Accès à la page " + PAGES.NEWS_ENTRY.name());
    return newsDisplayFactory.computeModelAndViewForNewsEntry(Locale.FRANCE, newsEntryId);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

}
