package com.cmpl.web.configuration.core.carousel;

import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.carousel.CarouselDAO;
import com.cmpl.web.core.carousel.CarouselDispatcherImpl;
import com.cmpl.web.core.carousel.CarouselMapper;
import com.cmpl.web.core.carousel.CarouselRepository;
import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.carousel.CarouselServiceImpl;
import com.cmpl.web.core.carousel.CarouselTranslator;
import com.cmpl.web.core.carousel.CarouselTranslatorImpl;
import com.cmpl.web.core.carousel.item.CarouselItemDAO;
import com.cmpl.web.core.carousel.item.CarouselItemMapper;
import com.cmpl.web.core.carousel.item.CarouselItemRepository;
import com.cmpl.web.core.carousel.item.CarouselItemService;
import com.cmpl.web.core.carousel.item.CarouselItemServiceImpl;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.carousel.CarouselManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class CarouselConfigurationTest {

  @Mock
  private CarouselItemDAO carouselItemDAO;

  @Mock
  private CarouselItemMapper carouselItemMapper;

  @Mock
  private CarouselDAO carouselDAO;

  @Mock
  private CarouselMapper carouselMapper;

  @Mock
  private CarouselItemRepository carouselItemRepository;
  @Mock
  private MediaService mediaService;
  @Mock
  private CarouselRepository carouselRepository;
  @Mock
  private CarouselItemService carouselItemService;
  @Mock
  private WebMessageSource messageSource;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private CarouselService carouselService;
  @Mock
  private CarouselTranslator carouselTranslator;

  @Mock
  private Set<Locale> availableLocales;

  @Mock
  private ApplicationEventPublisher publisher;

  @Mock
  private ContextHolder contextHolder;
  @Mock
  private PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;

  @Spy
  @InjectMocks
  private CarouselConfiguration configuration;

  @Test
  public void testCarouselItemService() throws Exception {
    Assert.assertEquals(CarouselItemServiceImpl.class,
        configuration.carouselItemService(carouselItemDAO, carouselItemMapper).getClass());
  }

  @Test
  public void testCarouselService() throws Exception {
    Assert.assertEquals(CarouselServiceImpl.class,
        configuration.carouselService(carouselDAO, carouselMapper).getClass());
  }

  @Test
  public void testCarouselTranslator() throws Exception {
    Assert.assertEquals(CarouselTranslatorImpl.class, configuration.carouselTranslator().getClass());
  }

  @Test
  public void testCarouselManagerDisplayFactory() throws Exception {
    Assert.assertEquals(CarouselManagerDisplayFactoryImpl.class,
        configuration.carouselManagerDisplayFactory(menuFactory, messageSource, carouselService, carouselItemService,
            mediaService, contextHolder, breadCrumbRegistry, availableLocales).getClass());
  }

  @Test
  public void testCarouselDispatcher() throws Exception {
    Assert.assertEquals(CarouselDispatcherImpl.class, configuration
        .carouselDispatcher(carouselService, carouselItemService, carouselTranslator, mediaService).getClass());
  }

}
