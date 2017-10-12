package cmpl.web.sitemap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.menu.MenuService;
import cmpl.web.message.WebMessageSource;

@RunWith(MockitoJUnitRunner.class)
public class SitemapConfigurationTest {

  @Mock
  private WebMessageSource messageSource;

  @Mock
  private MenuService menuService;

  @Spy
  SitemapConfiguration configuration;

  @Test
  public void testSitemapService() throws Exception {
    SitemapService result = configuration.sitemapService(menuService, messageSource);

    Assert.assertEquals(SitemapServiceImpl.class, result.getClass());
  }

}
