package com.cmpl.web.core.file;

import java.io.InputStream;

/**
 * Interface d'enregistrement de fichier
 *
 * @author Louis
 */
public interface FileService {

  /**
   * Enregistre un fichier sur le systeme dans le classpath Remplace un fichier existant avec le
   * contenu
   */
  void saveFileOnSystem(String fileName, String content);

  /**
   * Enregistre un media sur le systeme dans le classpath Remplace un fichier existant avec le
   * contenu
   */
  void saveMediaOnSystem(String fileName, byte[] content);

  /**
   * Lire le contenu d'un fichier du classpath
   */
  String readFileContentFromSystem(String fileName);

  /**
   * Lire le contenu d'un fichier dans un InputStream
   */
  InputStream read(String fileName);

  void removeFileFromSystem(String fileName);

  void removeMediaFromSystem(String fileName);

}
