package com.cmpl.web.core.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation de l'interface de gestion des images
 * 
 * @author Louis
 *
 */
public class ImageConverterServiceImpl implements ImageConverterService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageConverterServiceImpl.class);
  private static final String COMMA = ",";

  @Override
  public BufferedImage computeNewsImageFromString(String base64) {

    LOGGER.info("Conversion de l'image base64");
    try {
      return createBufferedImageFromBase64(base64);
    } catch (Exception e) {
      LOGGER.error("Impossible de parser l'image en paramètre", e);
      return null;
    }

  }

  BufferedImage createBufferedImageFromBase64(String base64) throws IOException {
    byte[] imageBytes = getImageByteArray(base64);
    return ImageIO.read(new ByteArrayInputStream(imageBytes));
  }

  @Override
  public byte[] getImageByteArray(String base64) throws IOException {
    String base64Image = base64.split(COMMA)[1];
    return DatatypeConverter.parseBase64Binary(base64Image);
  }

}
