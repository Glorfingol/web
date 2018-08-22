package com.cmpl.web.core.website;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.QWebsite;
import com.cmpl.web.core.models.Website;
import com.querydsl.core.types.Predicate;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultWebsiteDAO extends DefaultBaseDAO<Website> implements WebsiteDAO {

  private final WebsiteRepository websiteRepository;

  public DefaultWebsiteDAO(WebsiteRepository websiteRepository, ApplicationEventPublisher publisher) {
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
}
