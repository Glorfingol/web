package cmpl.web.page;

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

import cmpl.web.core.factory.DisplayFactory;
import cmpl.web.page.ContactController;

@RunWith(MockitoJUnitRunner.class)
public class ContactControllerTest {
  @Mock
  private DisplayFactory displayFactory;

  @Spy
  @InjectMocks
  private ContactController controller;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testPrintContacts() throws Exception {
    ModelAndView view = new ModelAndView("pages/contact");

    BDDMockito.doReturn(view).when(displayFactory)
        .computeModelAndViewForPage(Mockito.eq(PAGES.CONTACT), Mockito.eq(locale));

    ModelAndView result = controller.printContact();

    Assert.assertEquals(view, result);

    Mockito.verify(displayFactory, Mockito.times(1)).computeModelAndViewForPage(Mockito.eq(PAGES.CONTACT),
        Mockito.eq(locale));
  }

}
