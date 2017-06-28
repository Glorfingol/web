package cmpl.web.service;

import java.io.File;

import cmpl.web.model.BaseException;

/**
 * Interface pour gerer les fichiers
 * 
 * @author Louis
 *
 */
public interface FileService {

  /**
   * Enregistre une image sur le systeme
   * 
   * @param entityId
   * @param base64FileContent
   * @return
   * @throws BaseException
   */
  File saveFileOnSystem(String entityId, String base64FileContent) throws BaseException;

}
