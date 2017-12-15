package com.cmpl.web.media;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.page.BACK_PAGE;

public interface MediaManagerDisplayFactory {

  ModelAndView computeModelAndViewForViewAllMedias(BACK_PAGE backPage, Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForUploadMedia(BACK_PAGE backPage, Locale locale);

}
