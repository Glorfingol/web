package cmpl.web.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.translator.FacebookImportTranslator;
import cmpl.web.translator.NewsEntryTranslator;
import cmpl.web.translator.impl.FacebookImportTranslatorImpl;
import cmpl.web.translator.impl.NewsEntryTranslatorImpl;

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
