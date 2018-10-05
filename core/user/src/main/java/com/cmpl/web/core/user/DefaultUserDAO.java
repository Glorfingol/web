package com.cmpl.web.core.user;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.QUser;
import com.cmpl.web.core.models.User;
import com.querydsl.core.types.Predicate;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultUserDAO extends DefaultBaseDAO<User> implements UserDAO {

  private final UserRepository userRepository;

  public DefaultUserDAO(UserRepository entityRepository, ApplicationEventPublisher publisher) {
    super(User.class, entityRepository, publisher);
    this.userRepository = entityRepository;
  }

  @Override
  public User findByLogin(String login) {
    return userRepository.findByLogin(login);
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QUser user = QUser.user;
    return user.email.containsIgnoreCase(query).or(user.login.contains(query));
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
