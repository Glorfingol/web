package cmpl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.NewsDisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class NewsController {

  private final NewsDisplayFactory newsDisplayFactory;

  @Autowired
  NewsController(NewsDisplayFactory newsDisplayFactory) {
    this.newsDisplayFactory = newsDisplayFactory;
  }

  @RequestMapping(value = "/actualites")
  public ModelAndView printNews() {
    return newsDisplayFactory.computeModelAndViewForPage(PAGE.NEWS, "fr");
  }

}
