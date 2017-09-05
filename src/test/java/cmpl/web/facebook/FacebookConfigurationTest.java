package cmpl.web.facebook;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.footer.FooterFactory;
import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.news.NewsEntryService;

@RunWith(MockitoJUnitRunner.class)
public class FacebookConfigurationTest {

  @Mock
  FacebookImportService facebookImportService;

  @Mock
  FacebookImportTranslator facebookImportTranslator;

  @Mock
  private ContextHolder contextHolder;

  @Mock
  private MenuFactory menuFactory;

  @Mock
  private FooterFactory footerFactory;

  @Mock
  private MetaElementFactory metaElementFactory;

  @Mock
  private WebMessageSourceImpl messageSource;

  @Mock
  private FacebookService facebookService;

  @Mock
  private NewsEntryService newsEntryService;

  @Mock
  private Facebook facebookConnector;

  @Mock
  private ConnectionRepository connectionRepository;

  @Spy
  private FacebookConfiguration configuration;

  @Test
  public void testFacebookDispatcher() throws Exception {

    FacebookDispatcher result = configuration.facebookDispatcher(facebookImportService, facebookImportTranslator);

    Assert.assertEquals(FacebookDispatcherImpl.class, result.getClass());

  }

  @Test
  public void testFacebookImportTranslator() throws Exception {

    FacebookImportTranslator result = configuration.facebookImportTranslator();

    Assert.assertEquals(FacebookImportTranslatorImpl.class, result.getClass());
  }

  @Test
  public void testFacebookDisplayFactory() throws Exception {
    FacebookDisplayFactory result = configuration.facebookDisplayFactory(menuFactory, footerFactory, messageSource,
        facebookService, metaElementFactory);

    Assert.assertEquals(FacebookDisplayFactoryImpl.class, result.getClass());
  }

  @Test
  public void testFacebookService() throws Exception {
    FacebookService result = configuration.facebookService(contextHolder, facebookConnector, connectionRepository,
        newsEntryService);

    Assert.assertEquals(FacebookServiceImpl.class, result.getClass());
  }

  @Test
  public void testFacebookImportService() throws Exception {

    FacebookImportService result = configuration.facebookImportService(newsEntryService, facebookConnector,
        messageSource);

    Assert.assertEquals(FacebookImportServiceImpl.class, result.getClass());
  }

}
