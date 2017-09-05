package cmpl.web.footer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.message.WebMessageSourceImpl;

@RunWith(MockitoJUnitRunner.class)
public class FooterConfigurationTest {

  @Mock
  private WebMessageSourceImpl messageSource;

  @Spy
  FooterConfiguration configuration;

  @Test
  public void testFooterFactory() throws Exception {
    FooterFactory result = configuration.footerFactory(messageSource);

    Assert.assertEquals(FooterFactoryImpl.class, result.getClass());
  }

}
