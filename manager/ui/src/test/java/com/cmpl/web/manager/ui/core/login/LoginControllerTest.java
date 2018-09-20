package com.cmpl.web.manager.ui.core.login;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.login.LoginDisplayFactory;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import com.cmpl.web.core.user.UserDispatcher;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

  @Mock
  private LoginDisplayFactory displayFactory;

  @Mock
  private UserDispatcher userDispatcher;

  @Mock
  private WebMessageSource messageSource;

  @Mock
  private PluginRegistry<BackPage, String> backPages;

  @Spy
  @InjectMocks
  private LoginController controller;

  @Test
  public void testPrintLogin() throws Exception {

    ModelAndView loginView = new ModelAndView("back/login");
    BDDMockito.doReturn(loginView).when(displayFactory).computeModelAndViewForBackPage(BDDMockito.any(BackPage.class),
        BDDMockito.eq(Locale.FRANCE));

    BackPage backPage = BackPageBuilder.create().pageName("test").templatePath("somePath").titleKey("some.key")
        .decorated(true).build();
    BDDMockito.doReturn(backPage).when(controller).computeBackPage(BDDMockito.anyString());

    ModelAndView result = controller.printLogin();

    Assert.assertEquals(loginView, result);

    BDDMockito.verify(displayFactory, BDDMockito.times(1))
        .computeModelAndViewForBackPage(BDDMockito.any(BackPage.class), BDDMockito.eq(Locale.FRANCE));
  }
}
