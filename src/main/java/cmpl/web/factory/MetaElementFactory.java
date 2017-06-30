package cmpl.web.factory;

import java.util.List;
import java.util.Locale;

import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.page.PAGE;

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
  List<MetaElement> computeMetaElementsForPage(Locale locale, PAGE page);

  /**
   * Creer les open graph meta element pour les pages front
   * 
   * @param locale
   * @param page
   * @return
   */
  List<MetaElement> computeOpenGraphMetaElementsForPage(Locale locale, PAGE page);

  /**
   * Creer les meta elements pour une NewsEntry
   * 
   * @param locale
   * @param page
   * @param newsEntryId
   * @return
   */
  List<MetaElement> computeMetaElementsForNewsEntry(Locale locale, PAGE page, String newsEntryId);

  /**
   * Creer les open graph meta element pour une NewsEntry
   * 
   * @param locale
   * @param page
   * @param newsEntryId
   * @return
   */
  List<MetaElement> computeOpenGraphMetaElementsNewsEntry(Locale locale, PAGE page, String newsEntryId);

  /**
   * Creer les meta elements pour les pages back
   * 
   * @param locale
   * @return
   */
  List<MetaElement> computeMetaElementsForBackPage(Locale locale);

}
