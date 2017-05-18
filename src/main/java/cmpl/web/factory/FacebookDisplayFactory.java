package cmpl.web.factory;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.model.page.BACK_PAGE;

public interface FacebookDisplayFactory {

  ModelAndView computeModelAndViewForFacebookAccessPage(BACK_PAGE backPage, Locale locale);

  ModelAndView computeModelAndViewForFacebookImportPage(BACK_PAGE backPage, Locale locale);
}
