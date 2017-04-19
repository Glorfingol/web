package cmpl.web.factory;

import java.util.Locale;

import cmpl.web.model.footer.Footer;

public interface FooterFactory {

  Footer computeFooter(Locale locale);

}
