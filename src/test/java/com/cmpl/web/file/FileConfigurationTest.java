package com.cmpl.web.file;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.file.FileConfiguration;
import com.cmpl.web.file.ImageConverterService;
import com.cmpl.web.file.ImageConverterServiceImpl;
import com.cmpl.web.file.ImageService;
import com.cmpl.web.file.ImageServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class FileConfigurationTest {

  @Mock
  private ImageConverterService imageConverterService;

  @Mock
  private ContextHolder contextHolder;

  @Spy
  FileConfiguration configuration;

  @Test
  public void testImageService() throws Exception {
    ImageService result = configuration.imageService(contextHolder, imageConverterService);

    Assert.assertEquals(ImageServiceImpl.class, result.getClass());
  }

  @Test
  public void testImageConverterService() throws Exception {
    ImageConverterService result = configuration.imageConverterService();

    Assert.assertEquals(ImageConverterServiceImpl.class, result.getClass());
  }

}
