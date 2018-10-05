package com.cmpl.web.core.website;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.QSitemap;
import com.cmpl.web.core.models.QWebsite;
import com.cmpl.web.core.models.Website;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultWebsiteDAO extends DefaultBaseDAO<Website> implements WebsiteDAO {

  private final WebsiteRepository websiteRepository;

  public DefaultWebsiteDAO(WebsiteRepository websiteRepository,
    ApplicationEventPublisher publisher) {
    super(Website.class, websiteRepository, publisher);
    this.websiteRepository = websiteRepository;
  }

  @Override
  public Website getWebsiteByName(String websiteName) {
    return websiteRepository.findByName(websiteName);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QWebsite website = QWebsite.website;
    return website.name.containsIgnoreCase(query);
  }


  @Override
  protected Predicate computeLinkedPredicate(Long linkedToId) {
    QWebsite qWebsite = QWebsite.website;
    QSitemap qSitemap = QSitemap.sitemap;
    return qWebsite.id.in(new JPAQuery<>().from(qSitemap).select(qSitemap.websiteId)
      .where(qSitemap.pageId.eq(linkedToId))
      .distinct());
  }

  @Override
  protected Predicate computeNotLinkedPredicate(Long notLinkedToId) {
    QWebsite qWebsite = QWebsite.website;
    QSitemap qSitemap = QSitemap.sitemap;
    return qWebsite.id.notIn(new JPAQuery<>().from(qSitemap).select(qSitemap.websiteId)
      .where(qSitemap.pageId.eq(notLinkedToId))
      .distinct());
  }
}
