package cmpl.web.controller.front;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.NewsDisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class NewsController {

  private static final Logger LOGGER = LoggerFactory.getLogger(NewsController.class);
  private final NewsDisplayFactory newsDisplayFactory;

  @Autowired
  NewsController(NewsDisplayFactory newsDisplayFactory) {
    this.newsDisplayFactory = newsDisplayFactory;
  }

  @RequestMapping(value = "/actualites")
  public ModelAndView printNews() {

    LOGGER.info("Accès à la page " + PAGE.NEWS.name());
    return newsDisplayFactory.computeModelAndViewForPage(PAGE.NEWS, Locale.FRANCE);
  }

  @RequestMapping(value = "/actualites/{newsEntryId}")
  public ModelAndView printNewsEntry(@PathVariable(value = "newsEntryId") String newsEntryId) {

    LOGGER.info("Accès à la page " + PAGE.NEWS_ENTRY.name());
    return newsDisplayFactory.computeModelAndViewForNewsEntry(Locale.FRANCE, newsEntryId);
  }

}
