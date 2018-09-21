package com.cmpl.web.configuration.modules.facebook;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.DefaultWebMessageSource;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.facebook.DefaultFacebookDispatcher;
import com.cmpl.web.facebook.DefaultFacebookImportService;
import com.cmpl.web.facebook.DefaultFacebookImportTranslator;
import com.cmpl.web.facebook.DefaultFacebookService;
import com.cmpl.web.facebook.FacebookAdapter;
import com.cmpl.web.facebook.FacebookDispatcher;
import com.cmpl.web.facebook.FacebookImportService;
import com.cmpl.web.facebook.FacebookImportTranslator;
import com.cmpl.web.facebook.FacebookService;
import com.cmpl.web.modules.facebook.factory.DefaultFacebookDisplayFactory;
import com.cmpl.web.modules.facebook.factory.FacebookDisplayFactory;
import java.util.Locale;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

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
  private DefaultWebMessageSource messageSource;

  @Mock
  private Facebook facebookConnector;

  @Mock
  private NewsEntryService newsEntryService;

  @Mock
  private FacebookAdapter facebookAdapter;

  @Mock
  private MediaService mediaService;

  @Mock
  private FileService fileService;

  @Mock
  private ConnectionRepository connectionRepository;

  @Mock
  private PluginRegistry<BreadCrumb, String> breadCrumbRegistry;

  @Mock
  private Set<Locale> availableLocales;

  @Mock
  private GroupService groupService;

  @Mock
  private MembershipService membershipService;

  @Mock
  private PluginRegistry<BackPage, String> backPages;

  @Spy
  private FacebookConfiguration configuration;

  @Test
  public void testFacebookDispatcher() throws Exception {

    FacebookDispatcher result = configuration
        .facebookDispatcher(facebookImportService, facebookImportTranslator);

    Assert.assertEquals(DefaultFacebookDispatcher.class, result.getClass());

  }

  @Test
  public void testFacebookImportTranslator() throws Exception {

    FacebookImportTranslator result = configuration.facebookImportTranslator();

    Assert.assertEquals(DefaultFacebookImportTranslator.class, result.getClass());
  }

  @Test
  public void testFacebookDisplayFactory() throws Exception {
    FacebookDisplayFactory result = configuration
        .facebookDisplayFactory(menuFactory, messageSource, facebookAdapter,
            breadCrumbRegistry, availableLocales, backPages);

    Assert.assertEquals(DefaultFacebookDisplayFactory.class, result.getClass());
  }

  @Test
  public void testFacebookService() throws Exception {
    FacebookService result = configuration
        .facebookService(contextHolder, facebookConnector, connectionRepository,
            newsEntryService);

    Assert.assertEquals(DefaultFacebookService.class, result.getClass());
  }

  @Test
  public void testFacebookImportService() throws Exception {

    FacebookImportService result = configuration
        .facebookImportService(newsEntryService, facebookAdapter, mediaService,
            fileService, messageSource);

    Assert.assertEquals(DefaultFacebookImportService.class, result.getClass());
  }
}
