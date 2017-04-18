package cmpl.web.validator;

import cmpl.web.model.news.error.Error;
import cmpl.web.model.news.rest.news.NewsEntryRequest;

public interface NewsEntryRequestValidator {

  Error validateCreate(NewsEntryRequest request, String languageCode);

  Error validateUpdate(NewsEntryRequest request, String newsEntryId, String languageCode);

  Error validateDelete(String newsEntryId, String languageCode);

  Error validateGet(String newsEntryId, String languageCode);

}
