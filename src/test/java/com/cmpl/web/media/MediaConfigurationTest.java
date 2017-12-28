package com.cmpl.web.media;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.file.FileService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSourceImpl;

@RunWith(MockitoJUnitRunner.class)
public class MediaConfigurationTest {

  @Spy
  private MediaConfiguration configuration;

  @Mock
  private MediaRepository mediaRepository;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSourceImpl messageSource;
  @Mock
  private MediaService mediaService;
  @Mock
  private FileService fileService;
  @Mock
  private ContextHolder contextHolder;

  @Test
  public void testMediaService() throws Exception {
    Assert.assertEquals(MediaServiceImpl.class, configuration.mediaService(mediaRepository, fileService, contextHolder)
        .getClass());
  }

  @Test
  public void testMediaManagerDisplayFactory() throws Exception {
    Assert.assertEquals(MediaManagerDisplayFactoryImpl.class,
        configuration.mediaManagerDisplayFactory(menuFactory, messageSource, mediaService, contextHolder).getClass());
  }

}
