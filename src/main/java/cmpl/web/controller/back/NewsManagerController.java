package cmpl.web.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.BackDisplayFactory;
import cmpl.web.model.page.BACK_PAGE;

@Controller
public class NewsManagerController {

  private final BackDisplayFactory backDisplayFactory;

  @Autowired
  NewsManagerController(BackDisplayFactory backDisplayFactory) {
    this.backDisplayFactory = backDisplayFactory;
  }

  @RequestMapping(value = "/manager/news", method = RequestMethod.GET)
  public ModelAndView printViewNews() {

    return backDisplayFactory.computeModelAndViewForBackPage(BACK_PAGE.NEWS_VIEW, "fr");
  }
}
