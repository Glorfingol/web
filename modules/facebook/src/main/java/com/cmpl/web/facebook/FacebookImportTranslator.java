package com.cmpl.web.facebook;

import com.cmpl.web.core.news.entry.NewsEntryDTO;
import java.util.List;

/**
 * Translator pour les request d'import de post facebook
 *
 * @author Louis
 */
public interface FacebookImportTranslator {

  /**
   * Transforme les request REST en objet pour l'import
   */
  List<FacebookImportPost> fromRequestToPosts(FacebookImportRequest request);

  /**
   * Transforme les objets importes en reponse
   */
  FacebookImportResponse fromDTOToResponse(List<NewsEntryDTO> dtos);

}
