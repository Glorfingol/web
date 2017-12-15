package com.cmpl.web.meta;

import java.util.List;
import java.util.Locale;

/**
 * Interface pour la factory des meta elements du site pour le SEO
 * 
 * @author Louis
 *
 */
public interface MetaElementFactory {

  /**
   * Creer les meta elements pour les pages back
   * 
   * @param locale
   * @return
   */
  List<MetaElementToDelete> computeMetaElementsForBackPage(Locale locale);

}
