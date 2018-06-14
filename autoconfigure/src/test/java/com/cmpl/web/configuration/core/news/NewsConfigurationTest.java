package com.cmpl.web.configuration.core.news;

import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSourceImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.news.NewsManagerDisplayFactory;
import com.cmpl.web.core.factory.news.NewsManagerDisplayFactoryImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.news.content.NewsContentDAO;
import com.cmpl.web.core.news.content.NewsContentMapper;
import com.cmpl.web.core.news.content.NewsContentRepository;
import com.cmpl.web.core.news.content.NewsContentService;
import com.cmpl.web.core.news.content.NewsContentServiceImpl;
import com.cmpl.web.core.news.entry.NewsEntryDAO;
import com.cmpl.web.core.news.entry.NewsEntryDispatcher;
import com.cmpl.web.core.news.entry.NewsEntryDispatcherImpl;
import com.cmpl.web.core.news.entry.NewsEntryMapper;
import com.cmpl.web.core.news.entry.NewsEntryRepository;
import com.cmpl.web.core.news.entry.NewsEntryRequestValidator;
import com.cmpl.web.core.news.entry.NewsEntryRequestValidatorImpl;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.news.entry.NewsEntryServiceImpl;
import com.cmpl.web.core.news.entry.NewsEntryTranslator;
import com.cmpl.web.core.news.entry.NewsEntryTranslatorImpl;
import com.cmpl.web.core.news.image.NewsImageDAO;
import com.cmpl.web.core.news.image.NewsImageMapper;
import com.cmpl.web.core.news.image.NewsImageRepository;
import com.cmpl.web.core.news.image.NewsImageService;
import com.cmpl.web.core.news.image.NewsImageServiceImpl;
import com.cmpl.web.core.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class NewsConfigurationTest {

  @Mock
  NewsContentDAO newsContentDAO;

  @Mock
  NewsContentMapper newsContentMapper;

  @Mock
  NewsEntryDAO newsEntryDAO;

  @Mock
  NewsEntryMapper newsEntryMapper;

  @Mock
  NewsImageDAO newsImageDAO;

  @Mock
  NewsImageMapper newsImageMapper;

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
  private FileService fileService;

  @Mock
  private MediaService mediaService;

  @Mock
  private PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;

  @Mock
  private Set<Locale> availableLocales;

  @Mock
  private ApplicationEventPublisher publisher;

  @Spy
  private NewsConfiguration configuration;

  @Test
  public void testNewsEntryDispatcher() throws Exception {

    NewsEntryDispatcher result = configuration.newsEntryDispatcher(validator, translator, newsEntryService, fileService,
        mediaService);

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
        messageSource, newsEntryService, breadCrumbRegistry, availableLocales);

    Assert.assertEquals(NewsManagerDisplayFactoryImpl.class, result.getClass());
  }

  @Test
  public void testNewsEntryService() throws Exception {
    NewsEntryService result = configuration.newsEntryService(newsEntryDAO, newsEntryMapper, newsImageService,
        newsContentService);

    Assert.assertEquals(NewsEntryServiceImpl.class, result.getClass());
  }

  @Test
  public void testNewsImageService() throws Exception {
    NewsImageService result = configuration.newsImageService(newsImageDAO, newsImageMapper);

    Assert.assertEquals(NewsImageServiceImpl.class, result.getClass());

  }

  @Test
  public void testNewsContentService() throws Exception {
    NewsContentService result = configuration.newsContentService(newsContentDAO, newsContentMapper);

    Assert.assertEquals(NewsContentServiceImpl.class, result.getClass());
  }
}
