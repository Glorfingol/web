package com.cmpl.web.configuration.core.file;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.file.ImageConverterService;
import com.cmpl.web.core.file.ImageConverterServiceImpl;
import com.cmpl.web.core.file.ImageService;
import com.cmpl.web.core.file.ImageServiceImpl;

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
