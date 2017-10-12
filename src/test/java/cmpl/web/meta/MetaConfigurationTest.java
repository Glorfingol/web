package cmpl.web.meta;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.message.WebMessageSourceImpl;

@RunWith(MockitoJUnitRunner.class)
public class MetaConfigurationTest {

  @Mock
  private WebMessageSourceImpl messageSource;

  @Spy
  private MetaConfiguration configuration;

  @Test
  public void testMetaElementFactory() throws Exception {
    MetaElementFactory result = configuration.metaElementFactory(messageSource);

    Assert.assertEquals(MetaElementFactoryImpl.class, result.getClass());
  }

}
