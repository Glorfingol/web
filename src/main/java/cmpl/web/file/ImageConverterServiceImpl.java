package cmpl.web.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cmpl.web.news.NewsImageDTO;

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
  public NewsImageDTO computeNewsImageFromString(String base64) {

    LOGGER.info("Conversion de l'image base64");
    NewsImageDTO newsImage = new NewsImageDTO();

    try {
      BufferedImage bufferedImage = createBufferedImageFromBase64(base64);
      newsImage.setWidth(computeWidth(bufferedImage));
      newsImage.setHeight(computeHeight(bufferedImage));
    } catch (Exception e) {
      LOGGER.error("Impossible de parser l'image en paramètre", e);
      newsImage.setWidth(0);
      newsImage.setHeight(0);
    }

    return newsImage;
  }

  int computeWidth(BufferedImage bufferedImage) {
    return bufferedImage.getWidth();
  }

  int computeHeight(BufferedImage bufferedImage) {
    return bufferedImage.getHeight();
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
