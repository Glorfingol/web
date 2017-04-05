package cmpl.web.factory;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.model.page.BACK_PAGE;

public interface NewsManagerDisplayFactory extends BackDisplayFactory {

  ModelAndView computeModelAndViewForOneNewsEntry(BACK_PAGE backPage, String languageCode, String newsEntryId);

}
