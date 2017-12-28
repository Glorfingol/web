package com.cmpl.web.file;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.news.NewsImageDTO;

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
  public void testGetImageByteArray() throws Exception {
    String base64 = "png,lolo";

    service.getImageByteArray(base64);

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

    BDDMockito.doReturn(width).when(service).computeWidth(BDDMockito.eq(bufferedImage));
    BDDMockito.doReturn(height).when(service).computeHeight(BDDMockito.eq(bufferedImage));
    BDDMockito.doReturn(bufferedImage).when(service).createBufferedImageFromBase64(BDDMockito.anyString());

    NewsImageDTO result = service.computeNewsImageFromString("someBase64");

    Assert.assertEquals(width, result.getWidth());
    Assert.assertEquals(height, result.getHeight());

  }

  @Test
  public void testComputeNewsImageFromString_Exception() throws Exception {

    BDDMockito.doThrow(new IOException("")).when(service).createBufferedImageFromBase64(BDDMockito.anyString());

    NewsImageDTO result = service.computeNewsImageFromString("AQ==");
    Assert.assertEquals(0, result.getWidth());
    Assert.assertEquals(0, result.getHeight());
  }
}
