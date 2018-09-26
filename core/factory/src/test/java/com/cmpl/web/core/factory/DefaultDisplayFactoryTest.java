package com.cmpl.web.core.factory;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.page.RenderingPageDTO;
import com.cmpl.web.core.page.RenderingPageDTOBuilder;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
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
public class DefaultDisplayFactoryTest {

  @Mock
  private WebMessageSource messageSource;

  @Mock
  private DisplayFactoryCacheManager displayFactoryCacheManager;

  @Mock
  private PluginRegistry<WidgetProviderPlugin, String> widgetProviders;


  @Spy
  @InjectMocks
  private DefaultDisplayFactory displayFactory;

  @Test
  public void testComputePageFooter() throws Exception {

    RenderingPageDTO page = RenderingPageDTOBuilder.create().build();
    page.setName("test");

    Assert.assertEquals("test_footer_fr", displayFactory.computePageFooter(page, Locale.FRANCE));
  }

  @Test
  public void testComputePageHeader() throws Exception {

    RenderingPageDTO page = RenderingPageDTOBuilder.create().build();
    page.setName("test");

    Assert.assertEquals("test_header_fr", displayFactory.computePageHeader(page, Locale.FRANCE));
  }

  @Test
  public void testComputePageContent() throws Exception {

    RenderingPageDTO page = RenderingPageDTOBuilder.create().build();
    page.setName("test");

    Assert.assertEquals("test_fr", displayFactory.computePageContent(page, Locale.FRANCE));
  }


}
