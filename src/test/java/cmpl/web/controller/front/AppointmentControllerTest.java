package cmpl.web.controller.front;

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

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentControllerTest {

  @Mock
  private DisplayFactory displayFactory;

  @Spy
  @InjectMocks
  private AppointmentController controller;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testPrintAppointments() throws Exception {
    ModelAndView view = new ModelAndView("pages/rendez_vous");

    BDDMockito.doReturn(view).when(displayFactory)
        .computeModelAndViewForPage(Mockito.eq(PAGE.APPOINTMENT), Mockito.eq(locale));

    ModelAndView result = controller.printAppointments();

    Assert.assertEquals(view, result);

    Mockito.verify(displayFactory, Mockito.times(1)).computeModelAndViewForPage(Mockito.eq(PAGE.APPOINTMENT),
        Mockito.eq(locale));
  }

}
