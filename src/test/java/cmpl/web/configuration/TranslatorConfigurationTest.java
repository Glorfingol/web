package cmpl.web.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.facebook.FacebookImportTranslator;
import cmpl.web.facebook.FacebookImportTranslatorImpl;
import cmpl.web.news.NewsEntryTranslator;
import cmpl.web.news.NewsEntryTranslatorImpl;

@RunWith(MockitoJUnitRunner.class)
public class TranslatorConfigurationTest {

  @Spy
  private TranslatorConfiguration configuration;

  @Test
  public void testNewsEntryTranslator() throws Exception {
    NewsEntryTranslator result = configuration.newsEntryTranslator();

    Assert.assertEquals(NewsEntryTranslatorImpl.class, result.getClass());
  }

  @Test
  public void testFacebookImportTranslator() throws Exception {

    FacebookImportTranslator result = configuration.facebookImportTranslator();

    Assert.assertEquals(FacebookImportTranslatorImpl.class, result.getClass());
  }

}
