package cmpl.web.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.factory.BackDisplayFactory;
import cmpl.web.factory.CarouselFactory;
import cmpl.web.factory.DisplayFactory;
import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.factory.NewsDisplayFactory;
import cmpl.web.factory.NewsManagerDisplayFactory;
import cmpl.web.factory.impl.BackDisplayFactoryImpl;
import cmpl.web.factory.impl.CarouselFactoryImpl;
import cmpl.web.factory.impl.DisplayFactoryImpl;
import cmpl.web.factory.impl.FooterFactoryImpl;
import cmpl.web.factory.impl.MenuFactoryImpl;
import cmpl.web.factory.impl.MetaElementFactoryImpl;
import cmpl.web.factory.impl.NewsDisplayFactoryImpl;
import cmpl.web.factory.impl.NewsManagerDisplayFactoryImpl;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.service.NewsEntryService;

@RunWith(MockitoJUnitRunner.class)
public class FactoryConfigurationTest {

  @Mock
  private MenuFactory menuFactory;
  @Mock
  private FooterFactory footerFactory;
  @Mock
  private MetaElementFactory metaElementFactory;
  @Mock
  private CarouselFactory carouselFactory;
  @Mock
  private WebMessageSourceImpl messageSource;
  @Mock
  private NewsEntryService newsEntryService;

  @Spy
  private FactoryConfiguration configuration;

  @Test
  public void testDisplayFactory() throws Exception {

    DisplayFactory result = configuration.displayFactory(menuFactory, footerFactory, metaElementFactory,
        carouselFactory, messageSource);

    Assert.assertEquals(DisplayFactoryImpl.class, result.getClass());
  }

  @Test
  public void testBackdisplayFactory() throws Exception {
    BackDisplayFactory result = configuration.backdisplayFactory(menuFactory, footerFactory, messageSource,
        metaElementFactory);

    Assert.assertEquals(BackDisplayFactoryImpl.class, result.getClass());
  }

  @Test
  public void testNewsDisplayFactory() throws Exception {
    NewsDisplayFactory result = configuration.newsDisplayFactory(menuFactory, footerFactory, metaElementFactory,
        carouselFactory, messageSource, newsEntryService);

    Assert.assertEquals(NewsDisplayFactoryImpl.class, result.getClass());
  }

  @Test
  public void testNewsManagerDisplayFactory() throws Exception {
    NewsManagerDisplayFactory result = configuration.newsManagerDisplayFactory(menuFactory, footerFactory,
        messageSource, newsEntryService, metaElementFactory);

    Assert.assertEquals(NewsManagerDisplayFactoryImpl.class, result.getClass());
  }

  @Test
  public void testMenuFactory() throws Exception {
    MenuFactory result = configuration.menuFactory(messageSource);

    Assert.assertEquals(MenuFactoryImpl.class, result.getClass());

  }

  @Test
  public void testFooterFactory() throws Exception {
    FooterFactory result = configuration.footerFactory(messageSource);

    Assert.assertEquals(FooterFactoryImpl.class, result.getClass());
  }

  @Test
  public void testMetaElementFactory() throws Exception {
    MetaElementFactory result = configuration.metaElementFactory(messageSource);

    Assert.assertEquals(MetaElementFactoryImpl.class, result.getClass());
  }

  @Test
  public void testCarouselFactory() throws Exception {
    CarouselFactory result = configuration.carouselFactory();

    Assert.assertEquals(CarouselFactoryImpl.class, result.getClass());
  }

}
