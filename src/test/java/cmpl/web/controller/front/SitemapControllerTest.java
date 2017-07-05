package cmpl.web.controller.front;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.StringUtils;

import cmpl.web.model.BaseException;
import cmpl.web.service.SitemapService;

@RunWith(MockitoJUnitRunner.class)
public class SitemapControllerTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private SitemapService sitemapService;

  @Spy
  @InjectMocks
  private SitemapController controller;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testPrintSitemap_Ok() throws Exception {

    String sitemap = "someSitemap";

    BDDMockito.doReturn(sitemap).when(sitemapService).createSiteMap(Mockito.eq(locale));

    String result = controller.printSitemap();

    Assert.assertEquals(sitemap, result);
  }

  @Test
  public void testPrintSitemap_Ko() throws Exception {

    BDDMockito.doThrow(new BaseException("")).when(sitemapService).createSiteMap(Mockito.eq(locale));

    String result = controller.printSitemap();

    Assert.assertTrue(!StringUtils.hasText(result));

  }
}
