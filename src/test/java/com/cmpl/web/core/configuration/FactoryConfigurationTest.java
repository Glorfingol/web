package com.cmpl.web.core.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.carousel.CarouselService;
import com.cmpl.web.core.configuration.FactoryConfiguration;
import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.factory.DisplayFactory;
import com.cmpl.web.core.factory.DisplayFactoryImpl;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.news.NewsEntryService;
import com.cmpl.web.page.PageService;

@RunWith(MockitoJUnitRunner.class)
public class FactoryConfigurationTest {

  @Mock
  private MenuFactory menuFactory;
  @Mock
  private CarouselService carouselService;
  @Mock
  private WebMessageSourceImpl messageSource;
  @Mock
  private NewsEntryService newsEntryService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private PageService pageService;

  @Spy
  private FactoryConfiguration configuration;

  @Test
  public void testDisplayFactory() throws Exception {

    DisplayFactory result = configuration.displayFactory(menuFactory, carouselService, messageSource, pageService,
        newsEntryService, contextHolder);

    Assert.assertEquals(DisplayFactoryImpl.class, result.getClass());
  }

}
