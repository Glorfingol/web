package com.cmpl.web.sitemap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.menu.MenuService;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.sitemap.SitemapConfiguration;
import com.cmpl.web.sitemap.SitemapService;
import com.cmpl.web.sitemap.SitemapServiceImpl;

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
