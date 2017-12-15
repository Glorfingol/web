package com.cmpl.web.login;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.login.LoginConfiguration;
import com.cmpl.web.login.LoginDisplayFactory;
import com.cmpl.web.login.LoginDisplayFactoryImpl;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.menu.MenuService;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.meta.MetaElementFactory;

@RunWith(MockitoJUnitRunner.class)
public class LoginConfigurationTest {

  @Mock
  private MenuFactory menuFactory;

  @Mock
  private WebMessageSourceImpl messageSource;

  @Mock
  private MetaElementFactory metaElementFactory;

  @Mock
  private MenuService menuService;

  @Spy
  LoginConfiguration configuration;

  @Test
  public void testLogindisplayFactory() throws Exception {
    LoginDisplayFactory result = configuration.loginDisplayFactory(menuFactory, messageSource, metaElementFactory);

    Assert.assertEquals(LoginDisplayFactoryImpl.class, result.getClass());
  }
}
