package cmpl.web.meta;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.news.NewsEntryService;

@RunWith(MockitoJUnitRunner.class)
public class MetaConfigurationTest {

  @Mock
  NewsEntryService newsEntryService;

  @Mock
  private WebMessageSourceImpl messageSource;

  @Spy
  private MetaConfiguration configuration;

  @Test
  public void testMetaElementFactory() throws Exception {
    MetaElementFactory result = configuration.metaElementFactory(messageSource, newsEntryService);

    Assert.assertEquals(MetaElementFactoryImpl.class, result.getClass());
  }

}
