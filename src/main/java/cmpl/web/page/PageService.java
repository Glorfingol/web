package cmpl.web.page;

import java.util.List;

/**
 * Interface des pages
 * 
 * @author Louis
 *
 */
public interface PageService {

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
