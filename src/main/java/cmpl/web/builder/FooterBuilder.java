package cmpl.web.builder;

import java.util.Locale;

import cmpl.web.model.footer.Footer;

public interface FooterBuilder {

  Footer computeFooter(Locale locale);

}
