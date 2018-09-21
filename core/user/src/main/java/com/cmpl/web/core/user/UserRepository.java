package com.cmpl.web.core.user;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User> {

  User findByLogin(String login);

  User findByEmail(String email);
}
