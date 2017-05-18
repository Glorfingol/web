package cmpl.web.factory;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.model.page.BACK_PAGE;

public interface LoginDisplayFactory {

  ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, Locale locale);

}
