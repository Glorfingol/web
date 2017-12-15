package com.cmpl.web.core.factory;

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

import com.cmpl.web.core.factory.BaseDisplayFactoryImpl;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class BaseDisplayFactoryImplTest {

  @Mock
  private WebMessageSourceImpl messageSource;

  @InjectMocks
  @Spy
  private BaseDisplayFactoryImpl displayFactory;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeTileName() throws Exception {
    String tile = "login";

    BDDMockito.doReturn(tile).when(displayFactory).getI18nValue(Mockito.anyString(), Mockito.eq(locale));

    String result = displayFactory.computeTileName(BACK_PAGE.LOGIN.getTile(), locale);
    Assert.assertEquals(tile, result);
  }

  @Test
  public void testComputeHiddenLink() throws Exception {
    String href = "/";
    BDDMockito.doReturn(href).when(displayFactory).getI18nValue(Mockito.eq("back.pages.href"), Mockito.eq(locale));

    String result = displayFactory.computeHiddenLink(locale);

    Assert.assertEquals(href, result);

  }

  @Test
  public void testComputeDecoratorBack() throws Exception {
    String decoratorBack = "decorator_back";
    BDDMockito.doReturn(decoratorBack).when(displayFactory)
        .getI18nValue(Mockito.eq("decorator.back"), Mockito.eq(locale));

    String result = displayFactory.computeDecoratorBackTileName(locale);

    Assert.assertEquals(decoratorBack, result);

  }

}
