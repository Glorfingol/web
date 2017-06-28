package cmpl.web.service;

import java.util.List;
import java.util.Locale;

import cmpl.web.model.facebook.FacebookImportPost;
import cmpl.web.model.news.dto.NewsEntryDTO;

/**
 * Interface d'import de posts facebook en NewsEntry
 * 
 * @author Louis
 *
 */
public interface FacebookImportService {

  /**
   * Importer des posts facebook en NewsEntry et les remonter
   * 
   * @param facebookPosts
   * @param locale
   * @return
   */
  List<NewsEntryDTO> importFacebookPost(List<FacebookImportPost> facebookPosts, Locale locale);

}
