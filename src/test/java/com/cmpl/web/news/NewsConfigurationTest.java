package com.cmpl.web.news;

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
import com.cmpl.web.file.ImageConverterService;
import com.cmpl.web.file.ImageService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.page.BACK_PAGE;
import com.cmpl.web.page.PageService;

@RunWith(MockitoJUnitRunner.class)
public class NewsConfigurationTest {

  @Mock
  NewsEntryRequestValidator validator;

  @Mock
  NewsEntryTranslator translator;

  @Mock
  NewsEntryService newsEntryService;

  @Mock
  private WebMessageSourceImpl messageSource;

  @Mock
  private ContextHolder contextHolder;

  @Mock
  private MenuFactory menuFactory;

  @Mock
  private PageService pageService;

  @Mock
  private NewsEntryRepository newsEntryRepository;

  @Mock
  private NewsImageRepository newsImageRepository;

  @Mock
  private NewsContentRepository newsContentRepository;

  @Mock
  private NewsImageService newsImageService;

  @Mock
  private NewsContentService newsContentService;

  @Mock
  private ImageConverterService imageConverterService;

  @Mock
  private ImageService imageService;

  @Mock
  private FileService fileService;

  @Mock
  private PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;

  @Spy
  private NewsConfiguration configuration;

  @Test
  public void testNewsEntryDispatcher() throws Exception {

    NewsEntryDispatcher result = configuration
        .newsEntryDispatcher(validator, translator, newsEntryService, fileService);

    Assert.assertEquals(NewsEntryDispatcherImpl.class, result.getClass());
  }

  @Test
  public void testNewsEntryTranslator() throws Exception {
    NewsEntryTranslator result = configuration.newsEntryTranslator();

    Assert.assertEquals(NewsEntryTranslatorImpl.class, result.getClass());
  }

  @Test
  public void testNewsEntryRequestValidator() throws Exception {

    NewsEntryRequestValidator result = configuration.newsEntryRequestValidator(messageSource);

    Assert.assertEquals(NewsEntryRequestValidatorImpl.class, result.getClass());
  }

  @Test
  public void testNewsManagerDisplayFactory() throws Exception {
    NewsManagerDisplayFactory result = configuration.newsManagerDisplayFactory(contextHolder, menuFactory,
        messageSource, newsEntryService, fileService, breadCrumbRegistry);

    Assert.assertEquals(NewsManagerDisplayFactoryImpl.class, result.getClass());
  }

  @Test
  public void testNewsEntryService() throws Exception {
    NewsEntryService result = configuration.newsEntryService(newsEntryRepository, newsImageService, newsContentService,
        imageConverterService, imageService);

    Assert.assertEquals(NewsEntryServiceImpl.class, result.getClass());
  }

  @Test
  public void testNewsImageService() throws Exception {
    NewsImageService result = configuration.newsImageService(newsImageRepository);

    Assert.assertEquals(NewsImageServiceImpl.class, result.getClass());

  }

  @Test
  public void testNewsContentService() throws Exception {
    NewsContentService result = configuration.newsContentService(newsContentRepository);

    Assert.assertEquals(NewsContentServiceImpl.class, result.getClass());
  }
}