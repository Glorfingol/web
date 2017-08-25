package cmpl.web.facebook;

import java.util.List;
import java.util.Locale;

import cmpl.web.core.model.BaseException;
import cmpl.web.news.NewsEntryDTO;

/**
 * Implementation du dispatcher pour facebook import
 * 
 * @author Louis
 *
 */
public class FacebookDispatcherImpl implements FacebookDispatcher {

  private final FacebookImportService facebookImportService;
  private final FacebookImportTranslator facebookImportTranslator;

  private FacebookDispatcherImpl(FacebookImportService facebookImportService,
      FacebookImportTranslator facebookImportTranslator) {
    this.facebookImportService = facebookImportService;
    this.facebookImportTranslator = facebookImportTranslator;
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param facebookImportService
   * @param facebookImportTranslator
   * @return
   */
  public static FacebookDispatcherImpl fromService(FacebookImportService facebookImportService,
      FacebookImportTranslator facebookImportTranslator) {
    return new FacebookDispatcherImpl(facebookImportService, facebookImportTranslator);
  }

  @Override
  public FacebookImportResponse createEntity(FacebookImportRequest facebookImportRequest, Locale locale)
      throws BaseException {

    List<NewsEntryDTO> createdEntries = facebookImportService.importFacebookPost(
        facebookImportTranslator.fromRequestToPosts(facebookImportRequest), locale);
    return facebookImportTranslator.fromDTOToResponse(createdEntries);
  }

}
