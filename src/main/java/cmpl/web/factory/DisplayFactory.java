package cmpl.web.factory;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.model.page.PAGE;

public interface DisplayFactory {

  ModelAndView computeModelAndViewForPage(PAGE page, String languageCode);

}
