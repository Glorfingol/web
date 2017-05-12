package cmpl.web.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.message.WebMessageSource;
import cmpl.web.repository.NewsContentRepository;
import cmpl.web.repository.NewsEntryRepository;
import cmpl.web.repository.NewsImageRepository;
import cmpl.web.service.FileService;
import cmpl.web.service.ImageConverterService;
import cmpl.web.service.NewsContentService;
import cmpl.web.service.NewsEntryService;
import cmpl.web.service.NewsImageService;
import cmpl.web.service.SitemapService;
import cmpl.web.service.impl.FileServiceImpl;
import cmpl.web.service.impl.ImageConverterServiceImpl;
import cmpl.web.service.impl.NewsContentServiceImpl;
import cmpl.web.service.impl.NewsEntryServiceImpl;
import cmpl.web.service.impl.NewsImageServiceImpl;
import cmpl.web.service.impl.SitemapServiceImpl;

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
  private FileService fileService;

  @Spy
  private ServicesConfiguration configuration;

  @Test
  public void testNewsEntryService() throws Exception {
    NewsEntryService result = configuration.newsEntryService(newsEntryRepository, newsImageService, newsContentService,
        imageConverterService, fileService);

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
  public void testFileService() throws Exception {
    FileService result = configuration.fileService(imageConverterService);

    Assert.assertEquals(FileServiceImpl.class, result.getClass());
  }

}
