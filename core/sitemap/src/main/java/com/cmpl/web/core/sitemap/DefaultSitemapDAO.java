package com.cmpl.web.core.sitemap;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.Sitemap;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultSitemapDAO extends DefaultBaseDAO<Sitemap> implements SitemapDAO {

  private final SitemapRepository sitemapRepository;

  public DefaultSitemapDAO(SitemapRepository entityRepository,
    ApplicationEventPublisher publisher) {
    super(Sitemap.class, entityRepository, publisher);
    this.sitemapRepository = entityRepository;
  }

  @Override
  public List<Sitemap> findByWebsiteId(Long websiteId) {
    return sitemapRepository.findByWebsiteId(websiteId);
  }

  @Override
  public List<Sitemap> findByPageId(Long pageId) {
    return sitemapRepository.findByPageId(pageId);
  }

  @Override
  public Sitemap findByWebsiteIdAndPageId(Long websiteId, Long pageId) {
    return sitemapRepository.findByWebsiteIdAndPageId(websiteId, pageId);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected Predicate computeLinkedPredicate(Long linkedToId) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected Predicate computeNotLinkedPredicate(Long notLinkedToId) {
    throw new UnsupportedOperationException();
  }
}
