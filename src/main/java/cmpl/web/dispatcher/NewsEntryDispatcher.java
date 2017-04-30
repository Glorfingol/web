package cmpl.web.dispatcher;

import java.util.Locale;

import cmpl.web.model.BaseException;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsEntryResponse;

public interface NewsEntryDispatcher {

  NewsEntryResponse createEntity(NewsEntryRequest newsEntryRequest, Locale locale) throws BaseException;

  NewsEntryResponse updateEntity(NewsEntryRequest newsEntryRequest, String newsEntryId, Locale locale)
      throws BaseException;

  void deleteEntity(String newsEntryId, Locale locale) throws BaseException;

}
