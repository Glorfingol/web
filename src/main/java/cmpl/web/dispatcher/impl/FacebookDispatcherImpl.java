package cmpl.web.dispatcher.impl;

import java.util.List;
import java.util.Locale;

import cmpl.web.dispatcher.FacebookDispatcher;
import cmpl.web.model.BaseException;
import cmpl.web.model.facebook.FacebookImportRequest;
import cmpl.web.model.facebook.FacebookImportResponse;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.service.FacebookImportService;
import cmpl.web.translator.FacebookImportTranslator;

public class FacebookDispatcherImpl implements FacebookDispatcher {

  private final FacebookImportService facebookImportService;
  private final FacebookImportTranslator facebookImportTranslator;

  private FacebookDispatcherImpl(FacebookImportService facebookImportService,
      FacebookImportTranslator facebookImportTranslator) {
    this.facebookImportService = facebookImportService;
    this.facebookImportTranslator = facebookImportTranslator;
  }

  public static FacebookDispatcherImpl fromService(FacebookImportService facebookImportService,
      FacebookImportTranslator facebookImportTranslator) {
    return new FacebookDispatcherImpl(facebookImportService, facebookImportTranslator);
  }

  @Override
  public FacebookImportResponse createEntity(FacebookImportRequest facebookImportRequest, Locale locale)
      throws BaseException {

    List<NewsEntryDTO> createdEntries = facebookImportService.importFacebookPost(facebookImportTranslator
        .fromRequestToPosts(facebookImportRequest));
    return facebookImportTranslator.fromDTOToResponse(createdEntries);
  }

}
