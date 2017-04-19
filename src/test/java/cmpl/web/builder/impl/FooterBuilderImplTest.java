package cmpl.web.builder.impl;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.footer.Footer;

@RunWith(MockitoJUnitRunner.class)
public class FooterBuilderImplTest {

  private WebMessageSourceImpl messageBundle;

  private FooterBuilderImpl footerBuilder;

  private Locale locale;

  @Before
  public void setUp() {
    messageBundle = Mockito.mock(WebMessageSourceImpl.class);
    footerBuilder = FooterBuilderImpl.fromMessageSource(messageBundle);
    footerBuilder = Mockito.spy(footerBuilder);
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeFooter() throws Exception {

    String adresse = "14 rue Auber";
    String label = "un label";
    String phone = "0100000000";

    BDDMockito.doReturn(adresse).when(footerBuilder).getI18nValue(Mockito.eq("footer.address"), Mockito.eq(locale));
    BDDMockito.doReturn(label).when(footerBuilder).getI18nValue(Mockito.eq("footer.label"), Mockito.eq(locale));
    BDDMockito.doReturn(phone).when(footerBuilder).getI18nValue(Mockito.eq("footer.phone"), Mockito.eq(locale));

    Footer result = footerBuilder.computeFooter(locale);

    Assert.assertEquals(adresse, result.getAdresse());
    Assert.assertEquals(label, result.getLibelle());
    Assert.assertEquals(phone, result.getTelephone());
  }

}
