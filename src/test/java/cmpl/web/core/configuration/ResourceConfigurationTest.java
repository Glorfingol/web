package cmpl.web.core.configuration;

import java.util.Set;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.message.WebMessageSourceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ResourceConfigurationTest {

  @Spy
  private ResourceConfiguration configuration;

  @Test
  public void testMessageSource() throws Exception {

    Set<String> baseNames = Sets.newHashSet(Lists.newArrayList("i18n/footer", "i18n/keys", "i18n/back", "i18n/error",
        "i18n/form"));
    WebMessageSourceImpl result = configuration.messageSource();

    Assert.assertEquals(baseNames, result.getBasenameSet());

  }
}
