package com.cmpl.web.core.user;

import com.cmpl.web.core.common.mail.MailSender;
import com.cmpl.web.core.common.user.ActionTokenService;
import com.cmpl.web.core.models.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EntityScan(basePackageClasses = User.class)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class UserConfiguration {

  @Bean
  public UserMapper userMapper() {
    return new UserMapper();
  }

  @Bean
  public UserDAO userDAO(UserRepository userRepository, ApplicationEventPublisher publisher) {
    return new DefaultUserDAO(userRepository, publisher);
  }

  @Bean
  public UserService userService(UserMapper userMapper, UserDAO userDAO,
      ActionTokenService actionTokenService,
      UserMailService userMailService) {
    return new DefaultUserService(actionTokenService, userMailService, userDAO, userMapper);
  }

  @Bean
  public UserMailService userMailService(MailSender mailSender) {
    return new DefaultUserMailService(mailSender);
  }

  @Bean
  public UserTranslator userTranslator() {
    return new DefaultUserTranslator();
  }

  @Bean
  public UserDispatcher userDispatcher(UserTranslator userTranslator, UserService userService,
      PasswordEncoder passwordEncoder, ActionTokenService tokenService) {
    return new DefaultUserDispatcher(userTranslator, userService, passwordEncoder, tokenService);
  }

}
