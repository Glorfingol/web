package com.cmpl.web.facebook;

import com.cmpl.web.core.news.entry.NewsEntryDTO;
import java.util.List;
import java.util.Locale;

/**
 * Interface d'import de posts facebook en NewsEntry
 *
 * @author Louis
 */
public interface FacebookImportService {

  /**
   * Importer des posts facebook en NewsEntry et les remonter
   */
  List<NewsEntryDTO> importFacebookPost(List<FacebookImportPost> facebookPosts, Locale locale);

}
