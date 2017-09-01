package cmpl.web.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.facebook.FacebookImportService;
import cmpl.web.facebook.FacebookImportServiceImpl;
import cmpl.web.facebook.FacebookService;
import cmpl.web.facebook.FacebookServiceImpl;
import cmpl.web.file.ImageService;
import cmpl.web.file.ImageServiceImpl;
import cmpl.web.file.ImageConverterService;
import cmpl.web.file.ImageConverterServiceImpl;
import cmpl.web.message.WebMessageSource;
import cmpl.web.news.NewsContentRepository;
import cmpl.web.news.NewsContentService;
import cmpl.web.news.NewsContentServiceImpl;
import cmpl.web.news.NewsEntryRepository;
import cmpl.web.news.NewsEntryService;
import cmpl.web.news.NewsEntryServiceImpl;
import cmpl.web.news.NewsImageRepository;
import cmpl.web.news.NewsImageService;
import cmpl.web.news.NewsImageServiceImpl;
import cmpl.web.sitemap.SitemapService;
import cmpl.web.sitemap.SitemapServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ServicesConfigurationTest {

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
  private NewsEntryService newsEntryService;
  @Mock
  private ImageConverterService imageConverterService;
  @Mock
  private WebMessageSource messageSource;
  @Mock
  private ImageService imageService;
  @Mock
  private Facebook facebookConnector;
  @Mock
  private ConnectionRepository connectionRepository;
  @Mock
  private ContextHolder contextHolder;

  @Spy
  private ServicesConfiguration configuration;

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

  @Test
  public void testImageConverterService() throws Exception {
    ImageConverterService result = configuration.imageConverterService();

    Assert.assertEquals(ImageConverterServiceImpl.class, result.getClass());
  }

  @Test
  public void testSitemapService() throws Exception {
    SitemapService result = configuration.sitemapService(newsEntryService, messageSource);

    Assert.assertEquals(SitemapServiceImpl.class, result.getClass());
  }

  @Test
    public void testImageService() throws Exception {
      ImageService result = configuration.imageService(contextHolder, imageConverterService);
  
      Assert.assertEquals(ImageServiceImpl.class, result.getClass());
    }

  @Test
  public void testFacebookService() throws Exception {
    FacebookService result = configuration.facebookService(contextHolder, facebookConnector, connectionRepository,
        newsEntryService);

    Assert.assertEquals(FacebookServiceImpl.class, result.getClass());
  }

  @Test
  public void testFacebookImportService() throws Exception {

    FacebookImportService result = configuration.facebookImportService(newsEntryService, facebookConnector,
        messageSource);

    Assert.assertEquals(FacebookImportServiceImpl.class, result.getClass());
  }

}
