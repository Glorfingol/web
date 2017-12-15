package com.cmpl.web.core.controller;

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

import com.cmpl.web.core.controller.IndexController;
import com.cmpl.web.core.factory.DisplayFactory;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {

  @Mock
  private DisplayFactory displayFactory;

  @Spy
  @InjectMocks
  private IndexController controller;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testPrintIndex() throws Exception {
    ModelAndView view = new ModelAndView("pages/accueil");

    BDDMockito.doReturn(view).when(displayFactory)
        .computeModelAndViewForPage(Mockito.anyString(), Mockito.eq(locale), Mockito.anyInt());

    ModelAndView result = controller.printIndex();

    Assert.assertEquals(view, result);

    Mockito.verify(displayFactory, Mockito.times(1)).computeModelAndViewForPage(Mockito.anyString(),
        Mockito.eq(locale), Mockito.anyInt());
  }
}
