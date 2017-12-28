package com.cmpl.web.login;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.menu.MenuService;
import com.cmpl.web.message.WebMessageSourceImpl;

@RunWith(MockitoJUnitRunner.class)
public class LoginConfigurationTest {

  @Mock
  private MenuFactory menuFactory;

  @Mock
  private WebMessageSourceImpl messageSource;

  @Mock
  private MenuService menuService;

  @Spy
  LoginConfiguration configuration;

  @Test
  public void testLogindisplayFactory() throws Exception {
    LoginDisplayFactory result = configuration.loginDisplayFactory(menuFactory, messageSource);

    Assert.assertEquals(LoginDisplayFactoryImpl.class, result.getClass());
  }
}
