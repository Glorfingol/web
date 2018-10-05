package com.cmpl.web.core.design;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.Design;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultDesignDAO extends DefaultBaseDAO<Design> implements DesignDAO {

  private final DesignRepository designRepository;

  public DefaultDesignDAO(DesignRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Design.class, entityRepository, publisher);
    this.designRepository = entityRepository;
  }

  @Override
  public List<Design> findByWebsiteId(Long websiteId) {
    return designRepository.findByWebsiteId(websiteId);
  }

  @Override
  public List<Design> findByStyleId(Long styleId) {
    return designRepository.findByStyleId(styleId);
  }

  @Override
  public Design findByWebsiteIdAndStyleId(Long websiteId, Long styleId) {
    return designRepository.findByWebsiteIdAndStyleId(websiteId, styleId);
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
