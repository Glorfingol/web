package com.cmpl.web.media;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.file.FileService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.page.BACK_PAGE;

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
  @Mock
  private PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;

  @Test
  public void testMediaService() throws Exception {
    Assert.assertEquals(MediaServiceImpl.class, configuration.mediaService(mediaRepository, fileService, contextHolder)
        .getClass());
  }

  @Test
  public void testMediaManagerDisplayFactory() throws Exception {
    Assert.assertEquals(
        MediaManagerDisplayFactoryImpl.class,
        configuration.mediaManagerDisplayFactory(menuFactory, messageSource, mediaService, contextHolder,
            breadCrumbRegistry).getClass());
  }

}
