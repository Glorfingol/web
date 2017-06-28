package cmpl.web.translator;

import java.util.List;

import cmpl.web.model.facebook.FacebookImportPost;
import cmpl.web.model.facebook.FacebookImportRequest;
import cmpl.web.model.facebook.FacebookImportResponse;
import cmpl.web.model.news.dto.NewsEntryDTO;

/**
 * Translator pour les request d'import de post facebook
 * 
 * @author Louis
 *
 */
public interface FacebookImportTranslator {

  /**
   * Transforme les request REST en objet pour l'import
   * 
   * @param request
   * @return
   */
  List<FacebookImportPost> fromRequestToPosts(FacebookImportRequest request);

  /**
   * Transforme les objets importes en reponse
   * 
   * @param dtos
   * @return
   */
  FacebookImportResponse fromDTOToResponse(List<NewsEntryDTO> dtos);

}
