package com.cmpl.web.news;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.file.ImageConverterService;
import com.cmpl.web.file.ImageService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.meta.MetaElementFactory;
import com.cmpl.web.news.NewsConfiguration;
import com.cmpl.web.news.NewsContentRepository;
import com.cmpl.web.news.NewsContentService;
import com.cmpl.web.news.NewsContentServiceImpl;
import com.cmpl.web.news.NewsEntryDispatcher;
import com.cmpl.web.news.NewsEntryDispatcherImpl;
import com.cmpl.web.news.NewsEntryRepository;
import com.cmpl.web.news.NewsEntryRequestValidator;
import com.cmpl.web.news.NewsEntryRequestValidatorImpl;
import com.cmpl.web.news.NewsEntryService;
import com.cmpl.web.news.NewsEntryServiceImpl;
import com.cmpl.web.news.NewsEntryTranslator;
import com.cmpl.web.news.NewsEntryTranslatorImpl;
import com.cmpl.web.news.NewsImageRepository;
import com.cmpl.web.news.NewsImageService;
import com.cmpl.web.news.NewsImageServiceImpl;
import com.cmpl.web.news.NewsManagerDisplayFactory;
import com.cmpl.web.news.NewsManagerDisplayFactoryImpl;
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
  private MetaElementFactory metaElementFactory;

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

  @Spy
  private NewsConfiguration configuration;

  @Test
  public void testNewsEntryDispatcher() throws Exception {

    NewsEntryDispatcher result = configuration.newsEntryDispatcher(validator, translator, newsEntryService);

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
        messageSource, newsEntryService, metaElementFactory);

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
