package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.common.service.BaseService;

/**
 * Interface pour la gestion des NewsEntry
 *
 * @author Louis
 */
public interface NewsEntryService extends BaseService<NewsEntryDTO> {

  /**
   * Verifie si un post facebook a deja ete importe via son facebookId
   */
  boolean isAlreadyImportedFromFacebook(String facebookId);

}
