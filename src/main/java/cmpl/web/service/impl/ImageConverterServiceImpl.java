package cmpl.web.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;

import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.service.ImageConverterService;

public class ImageConverterServiceImpl implements ImageConverterService {

  private static final String COMMA = ",";
  private static final String FORMAT_PNG = "png";
  private static final String IMAGE_OVERHEAD = "data:image/{0};base64,";

  private ImageConverterServiceImpl() {

  }

  public static ImageConverterServiceImpl fromVoid() {
    return new ImageConverterServiceImpl();
  }

  @Override
  public NewsImageDTO computeNewsImageFromString(String base64) {

    NewsImageDTO newsImage = new NewsImageDTO();
    byte[] decodedSource = Base64.decodeBase64(base64);

    try {
      BufferedImage bufferedImage = createBufferedImageFromBase64(base64);
      newsImage.setWidth(computeWidth(bufferedImage));
      newsImage.setHeight(computeHeight(bufferedImage));
      newsImage.setSrc(getImageByteArray(base64));
      newsImage.setFormat(extractFormatFromBase64(base64));
    } catch (Exception e) {
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
    String base64;
    try {
      BufferedImage image = ImageIO.read(new ByteArrayInputStream(src));
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ImageIO.write(image, format, os);
      base64 = Base64.encodeBase64String(os.toByteArray());
    } catch (Exception e) {
      base64 = Base64.encodeBase64String(src);
    }
    return computeOverhead(format) + base64;
  }

  private String extractFormatFromBase64(String base64) {
    String firstMatcher = "data:image/";
    int previousIndex = base64.indexOf("data:image/");
    int nextIndex = base64.indexOf(";base64");

    return base64.substring(previousIndex + firstMatcher.length(), nextIndex);

  }

  private String computeOverhead(String format) {
    return MessageFormat.format(IMAGE_OVERHEAD, format);
  }

}
