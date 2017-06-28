package cmpl.web.translator.impl;

import java.util.List;

import cmpl.web.model.facebook.FacebookImportPost;
import cmpl.web.model.facebook.FacebookImportRequest;
import cmpl.web.model.facebook.FacebookImportResponse;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.translator.FacebookImportTranslator;

/**
 * Implementation du Translator pour les request d'import de post facebook
 * 
 * @author Louis
 *
 */
public class FacebookImportTranslatorImpl implements FacebookImportTranslator {

  private FacebookImportTranslatorImpl() {

  }

  /**
   * Constructeur static pour la configuration
   * 
   * @return
   */
  public static FacebookImportTranslatorImpl fromVoid() {
    return new FacebookImportTranslatorImpl();
  }

  @Override
  public List<FacebookImportPost> fromRequestToPosts(FacebookImportRequest request) {
    return request.getPostsToImport();
  }

  @Override
  public FacebookImportResponse fromDTOToResponse(List<NewsEntryDTO> dtos) {
    FacebookImportResponse response = new FacebookImportResponse();
    response.setCreatedNewsEntries(dtos);
    return response;
  }

}
