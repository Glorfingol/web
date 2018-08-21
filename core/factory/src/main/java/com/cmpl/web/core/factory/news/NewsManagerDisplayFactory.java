package com.cmpl.web.core.factory.news;

import com.cmpl.web.core.factory.CRUDBackDisplayFactory;
import java.util.Locale;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interface pour la factory des pages d'actualite sur le back
 *
 * @author Louis
 */
public interface NewsManagerDisplayFactory extends CRUDBackDisplayFactory {

  ModelAndView computeModelAndViewForOneNewsEntry(Locale locale, String newsEntryId);


  ModelAndView computeModelAndViewForViewAllNews(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForBackPageCreateNews(Locale locale);

  ModelAndView computeModelAndViewForUpdateNewsMain(String newsEntryId, Locale locale);

  ModelAndView computeModelAndViewForUpdateNewsContent(String newsEntryId, Locale locale);

  ModelAndView computeModelAndViewForUpdateNewsImage(String newsEntryId, Locale locale);

}
