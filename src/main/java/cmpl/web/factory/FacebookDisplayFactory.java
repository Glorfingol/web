package cmpl.web.factory;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

public interface FacebookDisplayFactory {

  ModelAndView computeModelAndViewForFacebookAccessPage(Locale locale);

  ModelAndView computeModelAndViewForFacebookImportPage(Locale locale);
}
