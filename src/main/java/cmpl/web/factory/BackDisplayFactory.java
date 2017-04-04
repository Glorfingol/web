package cmpl.web.factory;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.model.page.BACK_PAGE;

public interface BackDisplayFactory {

  ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, String languageCode);

}
