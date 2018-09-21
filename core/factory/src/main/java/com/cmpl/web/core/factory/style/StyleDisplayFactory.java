package com.cmpl.web.core.factory.style;

import com.cmpl.web.core.factory.CRUDBackDisplayFactory;
import java.util.Locale;
import org.springframework.web.servlet.ModelAndView;

public interface StyleDisplayFactory extends CRUDBackDisplayFactory {

  ModelAndView computeModelAndViewForViewAllStyles(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateStyle(Locale locale);

  ModelAndView computeModelAndViewForUpdateStyle(Locale locale, String styleId);

  ModelAndView computeModelAndViewForUpdateStyleMain(Locale locale, String styleId);

}
