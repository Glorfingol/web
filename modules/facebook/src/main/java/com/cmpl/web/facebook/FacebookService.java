package com.cmpl.web.facebook;

import com.cmpl.web.core.common.exception.BaseException;
import java.util.List;

/**
 * Interface pour recuperer les posts facebook d'un utilisateur
 *
 * @author Louis
 */
public interface FacebookService {

  /**
   * Recuperation des 25 derniers posts de l'utilisateur
   */
  List<ImportablePost> getRecentFeed() throws BaseException;

}
