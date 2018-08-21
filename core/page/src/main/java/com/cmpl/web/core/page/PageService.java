package com.cmpl.web.core.page;

import com.cmpl.web.core.common.service.BaseService;
import java.util.List;

/**
 * Interface des pages
 *
 * @author Louis
 */
public interface PageService extends BaseService<PageDTO> {

  /**
   * Trouver une page via son nom
   */
  PageDTO getPageByName(String pageName, String localeCode);

  /**
   * Remonter toutes les pages
   */
  List<PageDTO> getPages();

  PageDTO updateEntity(PageDTO dto, String localeCode);

  PageDTO createEntity(PageDTO dto, String localeCode);

  PageDTO getEntity(Long pageId, String localeCode);

}
