package com.cmpl.web.style;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.file.FileService;
import com.cmpl.web.media.MediaService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.meta.MetaElementFactory;

@RunWith(MockitoJUnitRunner.class)
public class StyleConfigurationTest {

  @Mock
  private StyleRepository styleRepository;
  @Mock
  private MediaService mediaService;
  @Mock
  private FileService fileService;
  @Mock
  private StyleTranslator styleTranslator;
  @Mock
  private StyleService styleService;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSource messageSource;
  @Mock
  private MetaElementFactory metaElementFactory;
  @Mock
  private ContextHolder contextHolder;

  @Spy
  @InjectMocks
  private StyleConfiguration configuration;

  @Test
  public void testStyleService() throws Exception {

    Assert.assertEquals(StyleServiceImpl.class, configuration.styleService(styleRepository, mediaService, fileService)
        .getClass());
  }

  @Test
  public void testStyleDispacther() throws Exception {
    Assert.assertEquals(StyleDispatcherImpl.class, configuration.styleDispacther(styleService, styleTranslator)
        .getClass());
  }

  @Test
  public void testStyleTranslator() throws Exception {
    Assert.assertEquals(StyleTranslatorImpl.class, configuration.styleTranslator().getClass());
  }

  @Test
  public void testStyleDisplayFactory() throws Exception {
    Assert.assertEquals(StyleDisplayFactoryImpl.class,
        configuration.styleDisplayFactory(menuFactory, messageSource, metaElementFactory, styleService, contextHolder)
            .getClass());
  }
}
