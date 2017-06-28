package cmpl.web.factory;

import java.util.Locale;

import cmpl.web.model.footer.Footer;

/**
 * Interface pour la factory du footer du site
 * 
 * @author Louis
 *
 */
public interface FooterFactory {

  /**
   * Creer le footer du site
   * 
   * @param locale
   * @return
   */
  Footer computeFooter(Locale locale);

}
