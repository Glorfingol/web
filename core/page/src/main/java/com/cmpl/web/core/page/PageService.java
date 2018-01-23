package com.cmpl.web.core.page;

import java.util.List;

import com.cmpl.web.core.common.service.BaseService;

/**
 * Interface des pages
 * 
 * @author Louis
 *
 */
public interface PageService extends BaseService<PageDTO> {

  /**
   * Trouver une page via son nom
   * 
   * @param pageName
   * @return
   */
  PageDTO getPageByName(String pageName);

  /**
   * Remonter toutes les pages
   * 
   * @return
   */
  List<PageDTO> getPages();

}
