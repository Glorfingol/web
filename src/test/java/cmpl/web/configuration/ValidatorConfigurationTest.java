package cmpl.web.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.news.NewsEntryRequestValidator;
import cmpl.web.news.NewsEntryRequestValidatorImpl;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorConfigurationTest {

  @Spy
  private ValidatorConfiguration configuration;

  @Mock
  private WebMessageSourceImpl messageSource;

  @Test
  public void testNewsEntryRequestValidator() throws Exception {

    NewsEntryRequestValidator result = configuration.newsEntryRequestValidator(messageSource);

    Assert.assertEquals(NewsEntryRequestValidatorImpl.class, result.getClass());
  }

}
