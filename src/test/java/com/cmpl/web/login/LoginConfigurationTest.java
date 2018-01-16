package com.cmpl.web.login;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.menu.MenuService;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class LoginConfigurationTest {

  @Mock
  private MenuFactory menuFactory;

  @Mock
  private WebMessageSourceImpl messageSource;

  @Mock
  private MenuService menuService;

  @Mock
  private PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;

  @Spy
  LoginConfiguration configuration;

  @Test
  public void testLogindisplayFactory() throws Exception {
    LoginDisplayFactory result = configuration.loginDisplayFactory(menuFactory, messageSource, breadCrumbRegistry);

    Assert.assertEquals(LoginDisplayFactoryImpl.class, result.getClass());
  }
}
