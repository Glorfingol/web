package com.cmpl.web.core.sitemap.rendering;

import com.cmpl.web.core.common.exception.BaseException;
import java.util.Locale;

/**
 * Interface gerant le sitemap
 *
 * @author Louis
 */
public interface RenderingSitemapService {

  /**
   * Creer un sitemap et renvoyer le contenu dans un String
   */
  String createSiteMap(String websiteName, Locale locale) throws BaseException;

}
