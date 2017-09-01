package cmpl.web.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.carousel.CarouselFactory;
import cmpl.web.carousel.CarouselFactoryImpl;
import cmpl.web.carousel.CarouselService;
import cmpl.web.core.context.ContextHolder;
import cmpl.web.core.factory.DisplayFactory;
import cmpl.web.core.factory.DisplayFactoryImpl;
import cmpl.web.facebook.FacebookDisplayFactory;
import cmpl.web.facebook.FacebookDisplayFactoryImpl;
import cmpl.web.facebook.FacebookService;
import cmpl.web.footer.FooterFactory;
import cmpl.web.footer.FooterFactoryImpl;
import cmpl.web.login.LoginDisplayFactory;
import cmpl.web.login.LoginDisplayFactoryImpl;
import cmpl.web.menu.MenuFactory;
import cmpl.web.menu.MenuFactoryImpl;
import cmpl.web.menu.MenuService;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.meta.MetaElementFactoryImpl;
import cmpl.web.news.NewsDisplayFactory;
import cmpl.web.news.NewsDisplayFactoryImpl;
import cmpl.web.news.NewsEntryService;
import cmpl.web.news.NewsManagerDisplayFactory;
import cmpl.web.news.NewsManagerDisplayFactoryImpl;
import cmpl.web.page.PageService;

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
  @Mock
  private FacebookService facebookService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private PageService pageService;
  @Mock
  private MenuService menuService;
  @Mock
  private CarouselService carouselService;

  @Spy
  private FactoryConfiguration configuration;

  @Test
  public void testDisplayFactory() throws Exception {

    DisplayFactory result = configuration.displayFactory(menuFactory, footerFactory, metaElementFactory,
        carouselFactory, messageSource, pageService, newsEntryService, contextHolder);

    Assert.assertEquals(DisplayFactoryImpl.class, result.getClass());
  }

  @Test
  public void testLogindisplayFactory() throws Exception {
    LoginDisplayFactory result = configuration.loginDisplayFactory(menuFactory, footerFactory, messageSource,
        metaElementFactory);

    Assert.assertEquals(LoginDisplayFactoryImpl.class, result.getClass());
  }

  @Test
  public void testNewsDisplayFactory() throws Exception {
    NewsDisplayFactory result = configuration.newsDisplayFactory(contextHolder, menuFactory, footerFactory,
        metaElementFactory, carouselFactory, messageSource, newsEntryService, pageService);

    Assert.assertEquals(NewsDisplayFactoryImpl.class, result.getClass());
  }

  @Test
  public void testNewsManagerDisplayFactory() throws Exception {
    NewsManagerDisplayFactory result = configuration.newsManagerDisplayFactory(contextHolder, menuFactory,
        footerFactory, messageSource, newsEntryService, metaElementFactory);

    Assert.assertEquals(NewsManagerDisplayFactoryImpl.class, result.getClass());
  }

  @Test
  public void testMenuFactory() throws Exception {
    MenuFactory result = configuration.menuFactory(messageSource, menuService);

    Assert.assertEquals(MenuFactoryImpl.class, result.getClass());

  }

  @Test
  public void testFooterFactory() throws Exception {
    FooterFactory result = configuration.footerFactory(messageSource);

    Assert.assertEquals(FooterFactoryImpl.class, result.getClass());
  }

  @Test
  public void testMetaElementFactory() throws Exception {
    MetaElementFactory result = configuration.metaElementFactory(messageSource, newsEntryService);

    Assert.assertEquals(MetaElementFactoryImpl.class, result.getClass());
  }

  @Test
  public void testCarouselFactory() throws Exception {
    CarouselFactory result = configuration.carouselFactory(carouselService);

    Assert.assertEquals(CarouselFactoryImpl.class, result.getClass());
  }

  @Test
  public void testFacebookDisplayFactory() throws Exception {
    FacebookDisplayFactory result = configuration.facebookDisplayFactory(menuFactory, footerFactory, messageSource,
        facebookService, metaElementFactory);

    Assert.assertEquals(FacebookDisplayFactoryImpl.class, result.getClass());
  }

}
