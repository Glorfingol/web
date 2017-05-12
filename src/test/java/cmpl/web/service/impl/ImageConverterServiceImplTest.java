package cmpl.web.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.model.news.dto.NewsImageDTO;

@RunWith(MockitoJUnitRunner.class)
public class ImageConverterServiceImplTest {

  @InjectMocks
  @Spy
  private ImageConverterServiceImpl service;

  @Test
  public void testComputeWidth() throws Exception {
    BufferedImage image = new BufferedImage(500, 600, BufferedImage.TYPE_INT_RGB);

    int result = service.computeWidth(image);
    Assert.assertEquals(500, result);
  }

  @Test
  public void testComputeHeight() throws Exception {
    BufferedImage image = new BufferedImage(500, 600, BufferedImage.TYPE_INT_RGB);

    int result = service.computeHeight(image);
    Assert.assertEquals(600, result);
  }

  @Test
  public void testToJPEG() throws Exception {

    Path path = Paths.get("src/test/resources/img/test.png");

    byte[] result = service.toJPEG(Files.readAllBytes(path));

    Assert.assertTrue(result[0] == Byte.parseByte("-1"));

  }

  @Test
  public void testGetImageByteArray_conversion() throws Exception {
    String base64 = "png,lolo";

    byte[] jpg = new byte[]{1};

    BDDMockito.doReturn(jpg).when(service).toJPEG(Mockito.any(byte[].class));
    service.getImageByteArray(base64);

    Mockito.verify(service, Mockito.times(1)).toJPEG(Mockito.any(byte[].class));

  }

  @Test
  public void testGetImageByteArray_no_conversion() throws Exception {
    String base64 = "jpg,lolo";

    service.getImageByteArray(base64);

    Mockito.verify(service, Mockito.times(0)).toJPEG(Mockito.any(byte[].class));

  }

  @Test
  public void testCreateBufferedImageFromBase64() throws Exception {

    String path = "src/test/resources/img/base64Image.txt";
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    String base64 = new String(encoded, "UTF-8");

    BufferedImage result = service.createBufferedImageFromBase64(base64);

    Assert.assertEquals(800, result.getWidth());
    Assert.assertEquals(600, result.getHeight());
  }

  @Test
  public void testComputeNewsImageFromString() throws Exception {
    int width = 100;
    int height = 100;
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    BDDMockito.doReturn(width).when(service).computeWidth(Mockito.eq(bufferedImage));
    BDDMockito.doReturn(height).when(service).computeHeight(Mockito.eq(bufferedImage));
    BDDMockito.doReturn(bufferedImage).when(service).createBufferedImageFromBase64(Mockito.anyString());

    NewsImageDTO result = service.computeNewsImageFromString("someBase64");

    Assert.assertEquals(width, result.getWidth());
    Assert.assertEquals(height, result.getHeight());

  }

  @Test
  public void testComputeNewsImageFromString_Exception() throws Exception {

    BDDMockito.doThrow(new IOException("")).when(service).createBufferedImageFromBase64(Mockito.anyString());

    NewsImageDTO result = service.computeNewsImageFromString("AQ==");
    Assert.assertEquals(0, result.getWidth());
    Assert.assertEquals(0, result.getHeight());
  }
}
