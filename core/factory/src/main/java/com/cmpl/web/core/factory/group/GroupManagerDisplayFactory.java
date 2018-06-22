package com.cmpl.web.core.factory.group;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

public interface GroupManagerDisplayFactory {

  ModelAndView computeModelAndViewForViewAllGroups(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateGroup(Locale locale);

  ModelAndView computeModelAndViewForUpdateGroup(Locale locale, String groupId);

  ModelAndView computeModelAndViewForUpdateGroupMain(Locale locale, String groupId);

}
