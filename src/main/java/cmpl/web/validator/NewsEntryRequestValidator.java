package cmpl.web.validator;

import java.util.Locale;

import cmpl.web.model.news.error.Error;
import cmpl.web.model.news.rest.news.NewsEntryRequest;

public interface NewsEntryRequestValidator {

  Error validateCreate(NewsEntryRequest request, Locale locale);

  Error validateUpdate(NewsEntryRequest request, String newsEntryId, Locale locale);

  Error validateDelete(String newsEntryId, Locale locale);

  Error validateGet(String newsEntryId, Locale locale);

}
