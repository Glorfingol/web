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

import cmpl.web.factory.NewsDisplayFactory;
import cmpl.web.model.page.PAGE;

@RunWith(MockitoJUnitRunner.class)
public class NewsControllerTest {

  @Mock
  private NewsDisplayFactory displayFactory;

  @Spy
  @InjectMocks
  private NewsController controller;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testPrintNews() throws Exception {

    ModelAndView view = new ModelAndView("pages/actualites");

    BDDMockito.doReturn(view).when(displayFactory)
        .computeModelAndViewForPage(Mockito.eq(PAGE.NEWS), Mockito.eq(locale));

    ModelAndView result = controller.printNews(0);

    Assert.assertEquals(view, result);

    Mockito.verify(displayFactory, Mockito.times(1)).computeModelAndViewForPage(Mockito.eq(PAGE.NEWS),
        Mockito.eq(locale));
  }

  @Test
  public void testPrintNewsEntry() throws Exception {

    ModelAndView view = new ModelAndView("pages/actualites/666");

    BDDMockito.doReturn(view).when(displayFactory)
        .computeModelAndViewForNewsEntry(Mockito.eq(locale), Mockito.eq("666"));

    ModelAndView result = controller.printNewsEntry("666");

    Assert.assertEquals(view, result);

    Mockito.verify(displayFactory, Mockito.times(1)).computeModelAndViewForNewsEntry(Mockito.eq(locale),
        Mockito.eq("666"));
  }
}
