package cmpl.web.core.factory;

import java.util.Locale;

/**
 * Interface commune de factory pour les pages
 * 
 * @author Louis
 *
 */
public interface BaseDisplayFactory extends BaseFactory {

  /**
   * Recupere le nom de la tile du decorateur du front
   * 
   * @param locale
   * @return
   */
  String computeDecoratorFrontTileName(Locale locale);

  /**
   * Recupere le nom de la tile pour le decorateur du back
   * 
   * @param locale
   * @return
   */
  String computeDecoratorBackTileName(Locale locale);

  /**
   * Recupere le title du site
   * 
   * @param locale
   * @return
   */
  String computeMainTitle(Locale locale);

  /**
   * Recupere le nom d'une tile a partir d'une cle i18n
   * 
   * @param tileName
   * @param locale
   * @return
   */
  String computeTileName(String tileName, Locale locale);

  /**
   * Recupere le lien pour le back office
   * 
   * @param locale
   * @return
   */
  String computeHiddenLink(Locale locale);
}
