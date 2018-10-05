package com.cmpl.web.core.page;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.Page;
import com.cmpl.web.core.models.QPage;
import com.cmpl.web.core.models.QSitemap;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
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
  public List<Page> getPages(Sort sort) {
    return pageRepository.findAll(sort);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QPage qPage = QPage.page;
    return qPage.name.containsIgnoreCase(query).or(qPage.menuTitle.containsIgnoreCase(query));
  }

  @Override
  protected Predicate computeLinkedPredicate(Long linkedToId) {
    QPage qPage = QPage.page;
    QSitemap qSitemap = QSitemap.sitemap;
    return qPage.id.in(new JPAQuery<>().from(qSitemap).select(qSitemap.pageId)
      .where(qSitemap.websiteId.eq(linkedToId))
      .distinct());
  }

  @Override
  protected Predicate computeNotLinkedPredicate(Long notLinkedToId) {
    QPage qPage = QPage.page;
    QSitemap qSitemap = QSitemap.sitemap;
    return qPage.id.notIn(new JPAQuery<>().from(qSitemap).select(qSitemap.pageId)
      .where(qSitemap.websiteId.eq(notLinkedToId))
      .distinct());
  }
}
