package com.cmpl.web.facebook;

import com.cmpl.web.core.news.entry.NewsEntryDTO;
import java.util.List;

/**
 * Implementation du Translator pour les request d'import de post facebook
 *
 * @author Louis
 */
public class DefaultFacebookImportTranslator implements FacebookImportTranslator {

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
