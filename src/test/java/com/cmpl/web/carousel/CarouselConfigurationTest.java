package com.cmpl.web.carousel;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.media.MediaService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.PageService;

@RunWith(MockitoJUnitRunner.class)
public class CarouselConfigurationTest {

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
  private CarouselValidator carouselValidator;
  @Mock
  private PageService pageService;
  @Mock
  private ContextHolder contextHolder;

  @Spy
  @InjectMocks
  private CarouselConfiguration configuration;

  @Test
  public void testCarouselItemService() throws Exception {
    Assert.assertEquals(CarouselItemServiceImpl.class,
        configuration.carouselItemService(carouselItemRepository, mediaService).getClass());
  }

  @Test
  public void testCarouselService() throws Exception {
    Assert.assertEquals(CarouselServiceImpl.class,
        configuration.carouselService(carouselRepository, carouselItemService).getClass());
  }

  @Test
  public void testCarouselTranslator() throws Exception {
    Assert.assertEquals(CarouselTranslatorImpl.class, configuration.carouselTranslator().getClass());
  }

  @Test
  public void testCarouselValidator() throws Exception {
    Assert.assertEquals(CarouselValidatorImpl.class, configuration.carouselValidator(messageSource).getClass());
  }

  @Test
  public void testCarouselManagerDisplayFactory() throws Exception {
    Assert.assertEquals(
        CarouselManagerDisplayFactoryImpl.class,
        configuration.carouselManagerDisplayFactory(menuFactory, messageSource, carouselService, carouselItemService,
            pageService, mediaService, contextHolder).getClass());
  }

  @Test
  public void testCarouselDispatcher() throws Exception {
    Assert.assertEquals(
        CarouselDispatcherImpl.class,
        configuration.carouselDispatcher(carouselService, carouselItemService, carouselTranslator, carouselValidator,
            mediaService).getClass());
  }

}
