package com.cmpl.web.facebook;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.news.NewsEntryService;
import com.cmpl.web.page.BACK_PAGE;

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
  private WebMessageSourceImpl messageSource;

  @Mock
  private FacebookService facebookService;

  @Mock
  private NewsEntryService newsEntryService;

  @Mock
  private Facebook facebookConnector;

  @Mock
  private ConnectionRepository connectionRepository;

  @Mock
  private PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;

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
    FacebookDisplayFactory result = configuration.facebookDisplayFactory(menuFactory, messageSource, facebookService,
        breadCrumbRegistry);

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
