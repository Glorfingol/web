package cmpl.web.login;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.menu.MenuFactory;
import cmpl.web.menu.MenuService;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;

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
