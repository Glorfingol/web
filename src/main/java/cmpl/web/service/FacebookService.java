package cmpl.web.service;

import java.util.List;

import cmpl.web.model.BaseException;
import cmpl.web.model.facebook.ImportablePost;

/**
 * Interface pour recuperer les posts facebook d'un utilisateur
 * 
 * @author Louis
 *
 */
public interface FacebookService {

  /**
   * Recuperation des 25 derniers posts de l'utilisateur
   * 
   * @return
   * @throws BaseException
   */
  List<ImportablePost> getRecentFeed() throws BaseException;

}
