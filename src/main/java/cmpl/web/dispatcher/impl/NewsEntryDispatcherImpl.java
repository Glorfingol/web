package cmpl.web.dispatcher.impl;

import cmpl.web.dispatcher.NewsEntryDispatcher;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.error.NewsEntryError;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsEntryResponse;
import cmpl.web.service.NewsEntryService;
import cmpl.web.translator.NewsEntryTranslator;
import cmpl.web.validator.NewsEntryRequestValidator;

public class NewsEntryDispatcherImpl implements NewsEntryDispatcher {

  private final NewsEntryRequestValidator validator;
  private final NewsEntryTranslator translator;
  private final NewsEntryService newsEntryService;

  private NewsEntryDispatcherImpl(NewsEntryRequestValidator validator, NewsEntryTranslator translator,
      NewsEntryService newsEntryService) {
    this.validator = validator;
    this.translator = translator;
    this.newsEntryService = newsEntryService;
  }

  public static NewsEntryDispatcherImpl fromValidatorAndTranslator(NewsEntryRequestValidator validator,
      NewsEntryTranslator translator, NewsEntryService newsEntryService) {
    return new NewsEntryDispatcherImpl(validator, translator, newsEntryService);
  }

  @Override
  public NewsEntryResponse createEntity(NewsEntryRequest newsEntryRequest, String languageCode) {

    NewsEntryError error = validator.validateCreate(newsEntryRequest, languageCode);

    if (error != null) {
      NewsEntryResponse response = new NewsEntryResponse();
      response.setError(error);
      return response;
    }

    NewsEntryDTO entityToCreate = translator.fromRequestToDTO(newsEntryRequest);
    NewsEntryDTO entityCreated = newsEntryService.createEntity(entityToCreate);

    return translator.fromDTOToResponse(entityCreated);
  }

  @Override
  public NewsEntryResponse updateEntity(NewsEntryRequest newsEntryRequest, String newsEntryId, String languageCode) {

    NewsEntryError error = validator.validateUpdate(newsEntryRequest, newsEntryId, languageCode);

    if (error != null) {
      NewsEntryResponse response = new NewsEntryResponse();
      response.setError(error);
      return response;
    }

    newsEntryRequest.setId(Long.parseLong(newsEntryId));
    NewsEntryDTO entityToUpdate = translator.fromRequestToDTO(newsEntryRequest);

    NewsEntryDTO entityUpdated = newsEntryService.updateEntity(entityToUpdate);

    return translator.fromDTOToResponse(entityUpdated);

  }

  @Override
  public void deleteEntity(String newsEntryId, String languageCode) throws Exception {
    NewsEntryError error = validator.validateDelete(newsEntryId, languageCode);
    if (error != null) {
      throw new Exception(error.getCauses().get(0).getMessage());
    }
    newsEntryService.deleteEntity(Long.parseLong(newsEntryId));
  }

}
