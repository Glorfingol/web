package cmpl.web.style;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.page.BACK_PAGE;

public interface StyleDisplayFactory {

  ModelAndView computeModelAndViewForViewStyles(BACK_PAGE backPage, Locale locale);

  ModelAndView computeModelAndViewForUpdateStyles(BACK_PAGE backPage, Locale locale);

}
