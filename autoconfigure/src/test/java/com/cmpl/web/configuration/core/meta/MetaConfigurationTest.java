package com.cmpl.web.configuration.core.meta;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.meta.MetaElementDispatcher;
import com.cmpl.web.core.meta.MetaElementDispatcherImpl;
import com.cmpl.web.core.meta.MetaElementRepository;
import com.cmpl.web.core.meta.MetaElementService;
import com.cmpl.web.core.meta.MetaElementServiceImpl;
import com.cmpl.web.core.meta.MetaElementTranslator;
import com.cmpl.web.core.meta.MetaElementTranslatorImpl;
import com.cmpl.web.core.meta.MetaElementValidator;
import com.cmpl.web.core.meta.MetaElementValidatorImpl;
import com.cmpl.web.core.meta.OpenGraphMetaElementRepository;
import com.cmpl.web.core.meta.OpenGraphMetaElementService;
import com.cmpl.web.core.meta.OpenGraphMetaElementServiceImpl;

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
