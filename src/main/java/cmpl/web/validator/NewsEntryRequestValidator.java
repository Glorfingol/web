package cmpl.web.validator;

import cmpl.web.model.news.error.NewsEntryError;
import cmpl.web.model.news.rest.news.NewsEntryRequest;

public interface NewsEntryRequestValidator {

  NewsEntryError validateCreate(NewsEntryRequest request, String languageCode);

  NewsEntryError validateUpdate(NewsEntryRequest request, String newsEntryId, String languageCode);

  NewsEntryError validateDelete(String newsEntryId, String languageCode);

  NewsEntryError validateGet(String newsEntryId, String languageCode);

}
