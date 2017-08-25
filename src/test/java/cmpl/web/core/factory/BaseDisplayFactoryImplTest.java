package cmpl.web.core.factory;

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

import cmpl.web.core.factory.BaseDisplayFactoryImpl;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.page.BACK_PAGE;

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
  public void testComputeMainTitle() throws Exception {

    String title = "title";

    BDDMockito.doReturn(title).when(displayFactory).getI18nValue(Mockito.anyString(), Mockito.eq(locale));

    String result = displayFactory.computeMainTitle(locale);

    Assert.assertEquals(title, result);
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
    BDDMockito.doReturn(href).when(displayFactory).getI18nValue(Mockito.eq("back.news.href"), Mockito.eq(locale));

    String result = displayFactory.computeHiddenLink(locale);

    Assert.assertEquals(href, result);

  }

  @Test
  public void testComputeDecoratorFront() throws Exception {
    String decoratorFront = "decorator_front";
    BDDMockito.doReturn(decoratorFront).when(displayFactory).getI18nValue(Mockito.eq("decorator.front"),
        Mockito.eq(locale));

    String result = displayFactory.computeDecoratorFrontTileName(locale);

    Assert.assertEquals(decoratorFront, result);

  }

  @Test
  public void testComputeDecoratorBack() throws Exception {
    String decoratorBack = "decorator_back";
    BDDMockito.doReturn(decoratorBack).when(displayFactory).getI18nValue(Mockito.eq("decorator.back"),
        Mockito.eq(locale));

    String result = displayFactory.computeDecoratorBackTileName(locale);

    Assert.assertEquals(decoratorBack, result);

  }

}
