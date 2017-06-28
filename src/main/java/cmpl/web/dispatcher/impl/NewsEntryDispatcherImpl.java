package cmpl.web.dispatcher.impl;

import java.util.Locale;

import cmpl.web.dispatcher.NewsEntryDispatcher;
import cmpl.web.model.BaseException;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.error.Error;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsEntryResponse;
import cmpl.web.service.NewsEntryService;
import cmpl.web.translator.NewsEntryTranslator;
import cmpl.web.validator.NewsEntryRequestValidator;

/**
 * Implementation du dispatcher pour les NewsEntry
 * 
 * @author Louis
 *
 */
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

  /**
   * Constructeur static pour la configuration
   * 
   * @param validator
   * @param translator
   * @param newsEntryService
   * @return
   */
  public static NewsEntryDispatcherImpl fromValidatorAndTranslator(NewsEntryRequestValidator validator,
      NewsEntryTranslator translator, NewsEntryService newsEntryService) {
    return new NewsEntryDispatcherImpl(validator, translator, newsEntryService);
  }

  @Override
  public NewsEntryResponse createEntity(NewsEntryRequest newsEntryRequest, Locale locale) {

    Error error = validator.validateCreate(newsEntryRequest, locale);

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
  public NewsEntryResponse updateEntity(NewsEntryRequest newsEntryRequest, String newsEntryId, Locale locale) {

    Error error = validator.validateUpdate(newsEntryRequest, newsEntryId, locale);

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
  public void deleteEntity(String newsEntryId, Locale locale) throws BaseException {
    Error error = validator.validateDelete(newsEntryId, locale);
    if (error != null) {
      throw new BaseException(error.getCauses().get(0).getMessage());
    }
    newsEntryService.deleteEntity(Long.parseLong(newsEntryId));
  }

}
