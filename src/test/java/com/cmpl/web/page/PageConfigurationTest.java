package com.cmpl.web.page;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.file.FileService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.meta.MetaElementService;
import com.cmpl.web.meta.OpenGraphMetaElementService;

@RunWith(MockitoJUnitRunner.class)
public class PageConfigurationTest {

  @Mock
  private ContextHolder contextHolder;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSource messageSource;
  @Mock
  private PageService pageService;
  @Mock
  private MetaElementService metaElementService;
  @Mock
  private OpenGraphMetaElementService openGraphMetaElementService;
  @Mock
  private PageValidator validator;
  @Mock
  private PageTranslator translator;
  @Mock
  private PageRepository pageRepository;
  @Mock
  private FileService fileService;

  @Spy
  @InjectMocks
  private PageConfiguration configuration;

  @Test
  public void testPageManagerDisplayFactory() throws Exception {

    Assert.assertEquals(
        PageManagerDisplayFactoryImpl.class,
        configuration.pageManagerDisplayFactory(contextHolder, menuFactory, messageSource, pageService,
            metaElementService, openGraphMetaElementService).getClass());
  }

  @Test
  public void testPageDispatcher() throws Exception {
    Assert.assertEquals(PageDispatcherImpl.class, configuration.pageDispatcher(validator, translator, pageService)
        .getClass());
  }

  @Test
  public void testPageService() throws Exception {
    Assert.assertEquals(PageServiceImpl.class,
        configuration.pageService(pageRepository, metaElementService, openGraphMetaElementService, fileService)
            .getClass());
  }

  @Test
  public void testPageValidator() throws Exception {
    Assert.assertEquals(PageValidatorImpl.class, configuration.pageValidator(messageSource).getClass());
  }

  @Test
  public void testPageTranslator() throws Exception {
    Assert.assertEquals(PageTranslatorImpl.class, configuration.pageTranslator().getClass());
  }

}
