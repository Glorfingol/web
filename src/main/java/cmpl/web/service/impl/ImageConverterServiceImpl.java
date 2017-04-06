package cmpl.web.service.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;

import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.service.ImageConverterService;

public class ImageConverterServiceImpl implements ImageConverterService {

  private static final String JPEG_FORMAT = "jpeg";
  private static final String JPEG_NAME = "jpeg";
  private static final String COMMA = ",";

  private ImageConverterServiceImpl() {

  }

  public static ImageConverterServiceImpl fromVoid() {
    return new ImageConverterServiceImpl();
  }

  @Override
  public NewsImageDTO computeNewsImageFromString(String base64) {

    NewsImageDTO newsImage = new NewsImageDTO();

    try {
      BufferedImage bufferedImage = createBufferedImageFromBase64(base64);
      newsImage.setWidth(computeWidth(bufferedImage));
      newsImage.setHeight(computeHeight(bufferedImage));
      String newSrc = computeSrcInJpeg(bufferedImage, base64);
      while (isResizeImage(newSrc)) {
        BufferedImage resizedImage = resizeImage(newSrc);
        newSrc = computeSrc(resizedImage);
        newsImage.setWidth(computeWidth(resizedImage));
        newsImage.setHeight(computeHeight(resizedImage));
      }
      newsImage.setSrc(newSrc);
    } catch (Exception e) {
      newsImage.setSrc(base64);
      newsImage.setWidth(0);
      newsImage.setHeight(0);
    }

    return newsImage;
  }

  private String computeSrcInJpeg(BufferedImage bufferedImage, String base64) throws IOException {
    if (base64.contains(JPEG_NAME)) {
      return base64;
    }
    return computeSrc(bufferedImage);
  }

  private String computeSrc(BufferedImage bufferedImage) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage, JPEG_FORMAT, outputStream);
    return Base64.encodeBase64String(outputStream.toByteArray());
  }

  private int computeWidth(BufferedImage bufferedImage) {
    return bufferedImage.getWidth();
  }

  private int computeHeight(BufferedImage bufferedImage) {
    return bufferedImage.getHeight();
  }

  private boolean isResizeImage(String base64) {
    return base64.length() > 65535;
  }

  private BufferedImage resizeImage(String base64) throws IOException {
    BufferedImage bufferedImage = createBufferedImageFromBase64(base64);
    int newWidth = bufferedImage.getWidth() - 10;
    int newHeight = bufferedImage.getHeight() - 10;
    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, bufferedImage.getType());
    Graphics2D g = resizedImage.createGraphics();
    g.drawImage(bufferedImage, 0, 0, newWidth, newHeight, null);
    g.dispose();

    return resizedImage;
  }

  private BufferedImage createBufferedImageFromBase64(String base64) throws IOException {
    String splittedBase64[] = splitBase64(base64);
    String base64Image = splittedBase64.length > 1 ? splittedBase64[1] : base64;
    byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);
    return ImageIO.read(new ByteArrayInputStream(imageBytes));
  }

  private String[] splitBase64(String base64) {
    return base64.split(COMMA);
  }
}
