package cmpl.web.factory.impl;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.builder.FooterBuilder;
import cmpl.web.builder.MenuBuilder;
import cmpl.web.builder.MetaElementBuilder;
import cmpl.web.message.impl.WebMessageSourceImpl;

@RunWith(MockitoJUnitRunner.class)
public class BackDisplayFactoryImplTest {

  private MenuBuilder menuBuilder;
  private FooterBuilder footerBuilder;
  private MetaElementBuilder metaElementBuilder;
  private WebMessageSourceImpl messageBundle;

  private BackDisplayFactoryImpl displayFactory;

  private Locale locale;

  @Before
  public void setUp() {
    footerBuilder = Mockito.mock(FooterBuilder.class);
    metaElementBuilder = Mockito.mock(MetaElementBuilder.class);
    menuBuilder = Mockito.mock(MenuBuilder.class);
    messageBundle = Mockito.mock(WebMessageSourceImpl.class);
    displayFactory = BackDisplayFactoryImpl.fromBuilders(menuBuilder, footerBuilder, messageBundle, metaElementBuilder);
    displayFactory = Mockito.spy(displayFactory);
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeMainTitle() throws Exception {

    String title = "title";

    BDDMockito.doReturn(title).when(displayFactory).computeI18nLabel(Mockito.anyString(), Mockito.eq(locale));

    String result = displayFactory.computeMainTitle(locale);

    Assert.assertEquals(title, result);
  }

}
