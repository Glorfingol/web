package com.cmpl.web.meta;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.message.WebMessageSource;

@RunWith(MockitoJUnitRunner.class)
public class MetaConfigurationTest {

  @Mock
  private MetaElementService metaElementService;

  @Mock
  private OpenGraphMetaElementService openGraphMetaElementService;

  @Mock
  private MetaElementDispatcher metaElementDispatcher;

  @Mock
  private MetaElementValidator metaElementValidator;

  @Mock
  private MetaElementTranslator metaElementTranslator;

  @Mock
  private MetaElementRepository metaElementRepository;

  @Mock
  private OpenGraphMetaElementRepository openGraphMetaElementrepository;

  @Mock
  private WebMessageSource messageSource;

  @Spy
  @InjectMocks
  private MetaConfiguration configuration;

  @Test
  public void testMetaElementService() throws Exception {
    Assert.assertEquals(MetaElementServiceImpl.class, configuration.metaElementService(metaElementRepository)
        .getClass());
  }

  @Test
  public void testOpenGraphMetaElementService() throws Exception {
    Assert.assertEquals(OpenGraphMetaElementServiceImpl.class,
        configuration.openGraphMetaElementService(openGraphMetaElementrepository).getClass());
  }

  @Test
  public void testMetaElementDispatcher() throws Exception {
    Assert.assertEquals(
        MetaElementDispatcherImpl.class,
        configuration.metaElementDispatcher(metaElementService, openGraphMetaElementService, metaElementTranslator,
            metaElementValidator).getClass());
  }

  @Test
  public void testMetaElementValidator() throws Exception {
    Assert.assertEquals(MetaElementValidatorImpl.class, configuration.metaElementValidator(messageSource).getClass());
  }

  @Test
  public void testMetaElementTranslator() throws Exception {
    Assert.assertEquals(MetaElementTranslatorImpl.class, configuration.metaElementTranslator().getClass());
  }

}
