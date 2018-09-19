package com.cmpl.web.core.page;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.Page;
import com.cmpl.web.core.models.QPage;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;

public class DefaultPageDAO extends DefaultBaseDAO<Page> implements PageDAO {

  private final PageRepository pageRepository;

  public DefaultPageDAO(PageRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Page.class, entityRepository, publisher);
    this.pageRepository = entityRepository;
  }

  @Override
  public Page getPageByName(String pageName) {
    return pageRepository.findByName(pageName);
  }

  @Override
  public List<Page> getPagesByHref(String href) {
    return pageRepository.findByHref(href);
  }

  @Override
  public List<Page> getPages(Sort sort) {
    return pageRepository.findAll(sort);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QPage qPage = QPage.page;
    return qPage.name.containsIgnoreCase(query).or(qPage.menuTitle.containsIgnoreCase(query));
  }
}
