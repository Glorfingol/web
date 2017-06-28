package cmpl.web.service;

import java.io.IOException;

import cmpl.web.model.news.dto.NewsImageDTO;

/**
 * Interface de gestion des images
 * 
 * @author Louis
 *
 */
public interface ImageConverterService {

  /**
   * Creer une NewsImageDTO a partir de donnees en base64
   * 
   * @param base64
   * @return
   */
  NewsImageDTO computeNewsImageFromString(String base64);

  /**
   * Convertir des donnees base64 en byte[]
   * 
   * @param base64
   * @return
   * @throws IOException
   */
  byte[] getImageByteArray(String base64) throws IOException;

}
