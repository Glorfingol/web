package com.cmpl.web.core.factory.website;

import com.cmpl.web.core.factory.CRUDBackDisplayFactory;
import java.util.Locale;
import org.springframework.web.servlet.ModelAndView;

public interface WebsiteManagerDisplayFactory extends CRUDBackDisplayFactory {

  ModelAndView computeModelAndViewForViewAllWebsites(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateWebsite(Locale locale);

  ModelAndView computeModelAndViewForUpdateWebsite(Locale locale, String websiteId);

  ModelAndView computeModelAndViewForUpdateWebsiteMain(Locale locale, String websiteId);

  ModelAndView computeModelAndViewForUpdateWebsiteSitemap(Locale locale, String websiteId);

  ModelAndView computeModelAndViewForUpdateWebsiteDesign(Locale locale, String websiteId);

  ModelAndView computeLinkedStyles(String websiteId, String query);

  ModelAndView computeLinkableStyles(String websiteId, String query);

  ModelAndView computeLinkedPages(String websiteId, String query);

  ModelAndView computeLinkablePages(String websiteId, String query);
}
