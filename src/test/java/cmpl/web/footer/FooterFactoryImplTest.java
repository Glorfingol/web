package cmpl.web.footer;

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

import cmpl.web.footer.FooterFactoryImpl;
import cmpl.web.message.WebMessageSourceImpl;

@RunWith(MockitoJUnitRunner.class)
public class FooterFactoryImplTest {

  @Mock
  private WebMessageSourceImpl messageSource;

  @InjectMocks
  @Spy
  private FooterFactoryImpl footerFactory;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeFooter() throws Exception {

    String rue = "14 rue Auber";
    String ville = "75009 paris";
    String label = "un label";
    String phone = "0100000000";
    String facebook = "https://facebook.com";
    String googlePlus = "https://plus.google.com";
    String email = "mailto:contact@cm-pl.com";

    BDDMockito.doReturn(rue).when(footerFactory).getI18nValue(Mockito.eq("footer.address.street"), Mockito.eq(locale));
    BDDMockito.doReturn(ville).when(footerFactory).getI18nValue(Mockito.eq("footer.address.city"), Mockito.eq(locale));
    BDDMockito.doReturn(label).when(footerFactory).getI18nValue(Mockito.eq("footer.label"), Mockito.eq(locale));
    BDDMockito.doReturn(phone).when(footerFactory).getI18nValue(Mockito.eq("footer.phone"), Mockito.eq(locale));
    BDDMockito.doReturn(facebook).when(footerFactory).getI18nValue(Mockito.eq("footer.facebook"), Mockito.eq(locale));
    BDDMockito.doReturn(googlePlus).when(footerFactory).getI18nValue(Mockito.eq("footer.google"), Mockito.eq(locale));
    BDDMockito.doReturn(email).when(footerFactory).getI18nValue(Mockito.eq("footer.email"), Mockito.eq(locale));

    Footer result = footerFactory.computeFooter(locale);

    Assert.assertEquals(rue, result.getRue());
    Assert.assertEquals(ville, result.getVille());
    Assert.assertEquals(label, result.getLibelle());
    Assert.assertEquals(phone, result.getTelephone());
    Assert.assertEquals(facebook, result.getFacebook());
    Assert.assertEquals(googlePlus, result.getGooglePlus());
    Assert.assertEquals(email, result.getEmail());
  }

}
