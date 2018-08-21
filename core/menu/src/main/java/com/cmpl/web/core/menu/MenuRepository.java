package com.cmpl.web.core.menu;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Menu;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * DAO Menu
 *
 * @author Louis
 */
@Repository
public interface MenuRepository extends BaseRepository<Menu> {

  /**
   * Trouver les enfants d'un menu
   */
  List<Menu> findByParentId(String parentId);

}
