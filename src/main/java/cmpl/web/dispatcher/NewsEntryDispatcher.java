package cmpl.web.dispatcher;

import cmpl.web.model.BaseException;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsEntryResponse;

public interface NewsEntryDispatcher {

  NewsEntryResponse createEntity(NewsEntryRequest newsEntryRequest, String languageCode) throws BaseException;

  NewsEntryResponse updateEntity(NewsEntryRequest newsEntryRequest, String newsEntryId, String languageCode)
      throws BaseException;

  void deleteEntity(String newsEntryId, String languageCode) throws BaseException;

}
