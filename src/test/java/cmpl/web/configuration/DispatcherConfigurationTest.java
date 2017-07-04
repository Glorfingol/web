package cmpl.web.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.dispatcher.FacebookDispatcher;
import cmpl.web.dispatcher.NewsEntryDispatcher;
import cmpl.web.dispatcher.impl.FacebookDispatcherImpl;
import cmpl.web.dispatcher.impl.NewsEntryDispatcherImpl;
import cmpl.web.service.FacebookImportService;
import cmpl.web.service.NewsEntryService;
import cmpl.web.translator.FacebookImportTranslator;
import cmpl.web.translator.NewsEntryTranslator;
import cmpl.web.validator.NewsEntryRequestValidator;

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
