package cmpl.web.footer;

import java.util.Locale;

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
