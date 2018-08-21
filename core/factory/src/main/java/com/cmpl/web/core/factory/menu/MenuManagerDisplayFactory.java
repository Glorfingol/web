package com.cmpl.web.core.factory.menu;

import com.cmpl.web.core.factory.CRUDBackDisplayFactory;
import java.util.Locale;
import org.springframework.web.servlet.ModelAndView;

public interface MenuManagerDisplayFactory extends CRUDBackDisplayFactory {

  ModelAndView computeModelAndViewForViewAllMenus(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateMenu(Locale locale);

  ModelAndView computeModelAndViewForUpdateMenuMain(String menuId);

  ModelAndView computeModelAndViewForUpdateMenu(Locale locale, String menuId);

}
