package cmpl.web.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.service.ImageConverterService;

public class ImageConverterServiceImpl implements ImageConverterService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageConverterServiceImpl.class);

  private static final String COMMA = ",";
  private static final String FORMAT_PNG = "png";
  private static final String FORMAT_JPG = "jpeg";
  private static final String IMAGE_OVERHEAD = "data:image/{0};base64,";

  private ImageConverterServiceImpl() {
  }

  public static ImageConverterServiceImpl fromVoid() {
    return new ImageConverterServiceImpl();
  }

  @Override
  public NewsImageDTO computeNewsImageFromString(String base64) {

    LOGGER.info("Conversion de l'image base64");
    NewsImageDTO newsImage = new NewsImageDTO();
    byte[] decodedSource = Base64.decodeBase64(base64);

    try {
      BufferedImage bufferedImage = createBufferedImageFromBase64(base64);
      newsImage.setWidth(computeWidth(bufferedImage));
      newsImage.setHeight(computeHeight(bufferedImage));
      newsImage.setSrc(getImageByteArray(base64));
      newsImage.setFormat(FORMAT_JPG);
    } catch (Exception e) {
      LOGGER.error("Impossible de parser l'image en paramètre", e);
      newsImage.setSrc(decodedSource);
      newsImage.setWidth(0);
      newsImage.setHeight(0);
      newsImage.setFormat(FORMAT_PNG);
    }

    return newsImage;
  }

  private int computeWidth(BufferedImage bufferedImage) {
    return bufferedImage.getWidth();
  }

  private int computeHeight(BufferedImage bufferedImage) {
    return bufferedImage.getHeight();
  }

  private BufferedImage createBufferedImageFromBase64(String base64) throws IOException {
    byte[] imageBytes = getImageByteArray(base64);
    return ImageIO.read(new ByteArrayInputStream(imageBytes));
  }

  private byte[] getImageByteArray(String base64) {
    String base64Image = base64.split(COMMA)[1];
    return DatatypeConverter.parseBase64Binary(base64Image);
  }

  @Override
  public String convertByteArrayToBase64(byte[] src, String format) {
    LOGGER.info("Conversion du byteArray en base64");
    String base64;
    try {
      BufferedImage image = ImageIO.read(new ByteArrayInputStream(src));
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ImageIO.write(image, format, os);
      base64 = Base64.encodeBase64String(os.toByteArray());
    } catch (Exception e) {
      LOGGER.error("Impossible de convertir le byte array en String pour le format " + format, e);
      base64 = Base64.encodeBase64String(src);
    }
    return computeOverhead(format) + base64;
  }

  private String computeOverhead(String format) {
    return MessageFormat.format(IMAGE_OVERHEAD, format);
  }

}
