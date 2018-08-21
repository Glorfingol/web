package com.cmpl.web.core.menu;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Menu;
import com.cmpl.web.core.models.QMenu;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;

public class MenuDAOImpl extends BaseDAOImpl<Menu> implements MenuDAO {

  private final MenuRepository menuRepository;

  public MenuDAOImpl(MenuRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Menu.class, entityRepository, publisher);
    this.menuRepository = entityRepository;
  }

  @Override
  public List<Menu> getMenus(Sort sort) {
    return menuRepository.findAll(sort);
  }

  @Override
  public List<Menu> findByParentId(String parentId) {
    return menuRepository.findByParentId(parentId);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QMenu qMenu = QMenu.menu;
    return qMenu.label.containsIgnoreCase(query).or(qMenu.title.containsIgnoreCase(query));
  }
}
