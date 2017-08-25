package cmpl.web.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.facebook.FacebookDispatcher;
import cmpl.web.facebook.FacebookDispatcherImpl;
import cmpl.web.facebook.FacebookImportService;
import cmpl.web.facebook.FacebookImportTranslator;
import cmpl.web.news.NewsEntryDispatcher;
import cmpl.web.news.NewsEntryDispatcherImpl;
import cmpl.web.news.NewsEntryRequestValidator;
import cmpl.web.news.NewsEntryService;
import cmpl.web.news.NewsEntryTranslator;

@RunWith(MockitoJUnitRunner.class)
public class DispatcherConfigurationTest {

  @Mock
  NewsEntryRequestValidator validator;

  @Mock
  NewsEntryTranslator translator;

  @Mock
  NewsEntryService newsEntryService;

  @Mock
  FacebookImportService facebookImportService;

  @Mock
  FacebookImportTranslator facebookImportTranslator;

  @Spy
  private DispatcherConfiguration configuration;

  @Test
  public void testNewsEntryDispatcher() throws Exception {

    NewsEntryDispatcher result = configuration.newsEntryDispatcher(validator, translator, newsEntryService);

    Assert.assertEquals(NewsEntryDispatcherImpl.class, result.getClass());
  }

  @Test
  public void testFacebookDispatcher() throws Exception {

    FacebookDispatcher result = configuration.facebookDispatcher(facebookImportService, facebookImportTranslator);

    Assert.assertEquals(FacebookDispatcherImpl.class, result.getClass());

  }
}
