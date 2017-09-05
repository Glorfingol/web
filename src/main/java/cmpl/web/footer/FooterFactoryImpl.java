package cmpl.web.footer;

import java.util.Locale;

import cmpl.web.core.factory.BaseFactoryImpl;
import cmpl.web.message.WebMessageSourceImpl;

/**
 * Implementation de l'interface pour la factory du footer du site
 * 
 * @author Louis
 *
 */
public class FooterFactoryImpl extends BaseFactoryImpl implements FooterFactory {

  public FooterFactoryImpl(WebMessageSourceImpl messageSource) {
    super(messageSource);
  }

  @Override
  public Footer computeFooter(Locale locale) {

    Footer footer = new Footer();

    footer.setRue(getI18nValue("footer.address.street", locale));
    footer.setVille(getI18nValue("footer.address.city", locale));
    footer.setLibelle(getI18nValue("footer.label", locale));
    footer.setTelephone(getI18nValue("footer.phone", locale));
    footer.setEmail(getI18nValue("footer.email", locale));
    footer.setFacebook(getI18nValue("footer.facebook", locale));
    footer.setGooglePlus(getI18nValue("footer.google", locale));

    return footer;
  }

}
