package cmpl.web.controller.back;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.LoginDisplayFactory;
import cmpl.web.model.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

  @Mock
  private LoginDisplayFactory displayFactory;

  @Spy
  @InjectMocks
  private LoginController controller;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testPrintLogin() throws Exception {

    ModelAndView loginView = new ModelAndView("back/login");
    BDDMockito.doReturn(loginView).when(displayFactory)
        .computeModelAndViewForBackPage(Mockito.eq(BACK_PAGE.LOGIN), Mockito.eq(locale));

    ModelAndView result = controller.printLogin();

    Assert.assertEquals(loginView, result);

    Mockito.verify(displayFactory, Mockito.times(1)).computeModelAndViewForBackPage(Mockito.eq(BACK_PAGE.LOGIN),
        Mockito.eq(locale));
  }
}
