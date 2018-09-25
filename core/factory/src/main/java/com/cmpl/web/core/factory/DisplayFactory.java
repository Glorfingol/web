package com.cmpl.web.core.factory;

import java.util.Locale;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interface de factory pur generer des model and view pour les pages du site
 *
 * @author Louis
 */
public interface DisplayFactory {


  ModelAndView computeModelAndViewForBlogEntry(String blogEntryId, String widgetId, Locale locale);

  ModelAndView computeModelAndViewForWidget(String widgetName, Locale locale, int pageNumber,
    String query);

  ModelAndView computeModelAndViewForWebsitePage(String websiteName, String pageHref, Locale locale,
    int pageNumber, String query);

  ModelAndView computeModelAndViewForWebsiteAMP(String websiteName, String pageHref, Locale locale,
    int pageNumber, String query);

}
