package com.cmpl.web.core.factory.group;

import com.cmpl.web.core.factory.CRUDBackDisplayFactory;
import java.util.Locale;
import org.springframework.web.servlet.ModelAndView;

public interface GroupManagerDisplayFactory extends CRUDBackDisplayFactory {

  ModelAndView computeModelAndViewForViewAllGroups(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateGroup(Locale locale);

  ModelAndView computeModelAndViewForUpdateGroup(Locale locale, String groupId);

  ModelAndView computeModelAndViewForUpdateGroupMain(Locale locale, String groupId);


}
