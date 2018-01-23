package com.cmpl.web.core.factory.media;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

public interface MediaManagerDisplayFactory {

  ModelAndView computeModelAndViewForViewAllMedias(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForUploadMedia(Locale locale);

}
