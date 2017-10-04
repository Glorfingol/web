package cmpl.web.menu;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.page.BACK_PAGE;

public interface MenuManagerDisplayFactory {

  ModelAndView computeModelAndViewForViewAllMenus(BACK_PAGE backPage, Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateMenu(BACK_PAGE backPage, Locale locale);

  ModelAndView computeModelAndViewForUpdateMenu(BACK_PAGE backPage, Locale locale, String menuId);

}
