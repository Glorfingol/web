package com.cmpl.web.front.ui.sitemap;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.sitemap.rendering.RenderingSitemapService;

@RunWith(MockitoJUnitRunner.class)
public class SitemapControllerTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private RenderingSitemapService renderingSitemapService;

  @Spy
  @InjectMocks
  private SitemapController controller;

  @Test
  public void testPrintSitemap_Ok() throws Exception {

    String sitemap = "someSitemap";

    BDDMockito.doReturn(sitemap).when(renderingSitemapService).createSiteMap(BDDMockito.eq(Locale.FRANCE));

    String result = controller.printSitemap(Locale.FRANCE);

    Assert.assertEquals(sitemap, result);
  }

  @Test
  public void testPrintSitemap_Ko() throws Exception {

    BDDMockito.doThrow(new BaseException("")).when(renderingSitemapService).createSiteMap(BDDMockito.eq(Locale.FRANCE));

    String result = controller.printSitemap(Locale.FRANCE);

    Assert.assertTrue(!StringUtils.hasText(result));

  }
}
