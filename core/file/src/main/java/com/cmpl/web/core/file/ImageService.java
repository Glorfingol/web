package com.cmpl.web.core.file;

import java.io.File;

import com.cmpl.web.core.common.exception.BaseException;

/**
 * Interface pour gerer les fichiers
 * 
 * @author Louis
 *
 */
public interface ImageService {

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
