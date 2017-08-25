package cmpl.web.meta;

import java.util.List;
import java.util.Locale;

import cmpl.web.page.PAGES;

/**
 * Interface pour la factory des meta elements du site pour le SEO
 * 
 * @author Louis
 *
 */
public interface MetaElementFactory {

  /**
   * Creer les meta elements pour les pages front
   * 
   * @param locale
   * @param page
   * @return
   */
  List<MetaElementToDelete> computeMetaElementsForPage(Locale locale, PAGES page);

  /**
   * Creer les open graph meta element pour les pages front
   * 
   * @param locale
   * @param page
   * @return
   */
  List<MetaElementToDelete> computeOpenGraphMetaElementsForPage(Locale locale, PAGES page);

  /**
   * Creer les meta elements pour une NewsEntry
   * 
   * @param locale
   * @param page
   * @param newsEntryId
   * @return
   */
  List<MetaElementToDelete> computeMetaElementsForNewsEntry(Locale locale, PAGES page, String newsEntryId);

  /**
   * Creer les open graph meta element pour une NewsEntry
   * 
   * @param locale
   * @param page
   * @param newsEntryId
   * @return
   */
  List<MetaElementToDelete> computeOpenGraphMetaElementsNewsEntry(Locale locale, PAGES page, String newsEntryId);

  /**
   * Creer les meta elements pour les pages back
   * 
   * @param locale
   * @return
   */
  List<MetaElementToDelete> computeMetaElementsForBackPage(Locale locale);

}
