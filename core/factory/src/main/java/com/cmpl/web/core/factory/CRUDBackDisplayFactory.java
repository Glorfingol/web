package com.cmpl.web.core.factory;

import java.util.Locale;
import org.springframework.web.servlet.ModelAndView;

public interface CRUDBackDisplayFactory extends BackDisplayFactory {

  ModelAndView computeModelAndViewForMembership(String entityId);

  ModelAndView computeModelAndViewForAllEntitiesTab(Locale locale, int pageNumber, String query);

}
