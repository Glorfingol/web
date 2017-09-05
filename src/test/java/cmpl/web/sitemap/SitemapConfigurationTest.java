package cmpl.web.sitemap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.message.WebMessageSource;
import cmpl.web.news.NewsEntryService;

@RunWith(MockitoJUnitRunner.class)
public class SitemapConfigurationTest {

  @Mock
  private WebMessageSource messageSource;

  @Mock
  private NewsEntryService newsEntryService;

  @Spy
  SitemapConfiguration configuration;

  @Test
  public void testSitemapService() throws Exception {
    SitemapService result = configuration.sitemapService(newsEntryService, messageSource);

    Assert.assertEquals(SitemapServiceImpl.class, result.getClass());
  }

}
