package com.cmpl.web.core.factory.media;

import com.cmpl.web.core.factory.CRUDBackDisplayFactory;
import java.util.Locale;
import org.springframework.web.servlet.ModelAndView;

public interface MediaManagerDisplayFactory extends CRUDBackDisplayFactory {

  ModelAndView computeModelAndViewForViewAllMedias(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForViewMedia(String mediaId, Locale locale);

  ModelAndView computeModelAndViewForViewMediaMain(String mediaId);

  ModelAndView computeModelAndViewForUploadMedia(Locale locale);

}
