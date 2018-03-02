package com.cmpl.web.configuration.core.user;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.common.mail.MailSender;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.user.User;
import com.cmpl.web.core.user.UserMailService;
import com.cmpl.web.core.user.UserMailServiceImpl;
import com.cmpl.web.core.user.UserRepository;
import com.cmpl.web.core.user.UserService;
import com.cmpl.web.core.user.UserServiceImpl;

@Configuration
@EntityScan(basePackageClasses = User.class)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class UserConfiguration {

  @Bean
  UserService userService(UserRepository userRepository) {
    return new UserServiceImpl(userRepository);
  }

  @Bean
  UserMailService userMailService(MailSender mailSender) {
    return new UserMailServiceImpl(mailSender);
  }

  @Bean
  BackMenuItem userBackMenuItem(BackMenuItem administration, Privilege usersReadPrivilege) {
    return BackMenuItemBuilder.create().href("back.users.href").label("back.users.label").title("back.users.title")
        .iconClass("fa fa-user").parent(administration).privilege(usersReadPrivilege.privilege()).order(0).build();
  }
}
