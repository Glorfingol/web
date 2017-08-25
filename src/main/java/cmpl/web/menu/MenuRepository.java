package cmpl.web.menu;

import java.util.List;

import org.springframework.stereotype.Repository;

import cmpl.web.core.repository.BaseRepository;

/**
 * DAO Menu
 * 
 * @author Louis
 *
 */
@Repository
public interface MenuRepository extends BaseRepository<Menu> {

  /**
   * Trouver les enfants d'un menu
   * 
   * @param parentId
   * @return
   */
  List<Menu> findByParentId(String parentId);

}
