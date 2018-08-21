package com.cmpl.web.configuration.core.common;

import com.cmpl.web.core.common.message.WebMessageSourceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResourceConfigurationTest {

  @Spy
  private ResourceConfiguration configuration;

  @Test
  public void testMessageSource() throws Exception {
    Assert.assertEquals(WebMessageSourceImpl.class, configuration.messageSource().getClass());

  }
}
