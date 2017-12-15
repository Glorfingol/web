package com.cmpl.web.menu;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.menu.MenuConfiguration;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.menu.MenuFactoryImpl;
import com.cmpl.web.menu.MenuService;
import com.cmpl.web.message.WebMessageSourceImpl;

@RunWith(MockitoJUnitRunner.class)
public class MenuCongifurationTest {

  @Mock
  private WebMessageSourceImpl messageSource;

  @Mock
  private MenuService menuService;

  @Spy
  MenuConfiguration configuration;

  @Test
  public void testMenuFactory() throws Exception {
    MenuFactory result = configuration.menuFactory(messageSource, menuService);

    Assert.assertEquals(MenuFactoryImpl.class, result.getClass());

  }

}
