package com.cmpl.web.core.factory;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageDTOBuilder;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.style.StyleService;
import com.cmpl.web.core.website.WebsiteService;
import com.cmpl.web.core.widget.WidgetService;
import com.cmpl.web.core.widget.page.WidgetPageService;
import java.util.Locale;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.plugin.core.PluginRegistry;

@RunWith(MockitoJUnitRunner.class)
public class DisplayFactoryImplTest {

  @Mock
  private MenuFactory menuFactory;

  @Mock
  private WebMessageSource messageSource;

  @Mock
  private PageService pageService;

  @Mock
  private NewsEntryService newsEntryService;

  @Mock
  private ContextHolder contextHolder;

  @Mock
  private WidgetPageService widgetPageService;

  @Mock
  private WidgetService widgetService;

  @Mock
  private PluginRegistry<WidgetProviderPlugin, String> widgetProviders;

  @Mock
  private WebsiteService websiteService;

  @Mock
  private SitemapService sitemapService;

  @Mock
  private DesignService designService;

  @Mock
  private StyleService styleService;

  @Spy
  @InjectMocks
  private DisplayFactoryImpl displayFactory;

  @Test
  public void testComputePageFooter() throws Exception {

    PageDTO page = PageDTOBuilder.create().build();
    page.setName("test");

    Assert.assertEquals("test_footer_fr", displayFactory.computePageFooter(page, Locale.FRANCE));
  }

  @Test
  public void testComputePageHeader() throws Exception {

    PageDTO page = PageDTOBuilder.create().build();
    page.setName("test");

    Assert.assertEquals("test_header_fr", displayFactory.computePageHeader(page, Locale.FRANCE));
  }

  @Test
  public void testComputePageContent() throws Exception {

    PageDTO page = PageDTOBuilder.create().build();
    page.setName("test");

    Assert.assertEquals("test_fr", displayFactory.computePageContent(page, Locale.FRANCE));
  }


}
