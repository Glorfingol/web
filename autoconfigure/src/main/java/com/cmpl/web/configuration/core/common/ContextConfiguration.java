package com.cmpl.web.configuration.core.common;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.responsibility.ResponsibilityService;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.user.UserService;
import com.cmpl.web.manager.ui.core.administration.user.DefaultLastConnectionUpdateAuthenticationSuccessHandler;
import com.cmpl.web.manager.ui.core.common.security.DefaultCurrentUserDetailsService;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * COnfiguration du contextHolder a partir de donnes du fichier yaml
 *
 * @author Louis
 */
@Configuration
@PropertySource("classpath:/core/core.properties")
@EnablePluginRegistries({Privilege.class})
public class ContextConfiguration {

  @Value("${templateBasePath}")
  String templateBasePath;

  @Value("${mediaBasePath}")
  String mediaBasePath;

  @Bean
  public ContextHolder contextHolder() {

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy");

    ContextHolder contextHolder = new ContextHolder();

    contextHolder.setDateFormat(dateFormat);
    contextHolder.setElementsPerPage(10);
    contextHolder.setTemplateBasePath(templateBasePath);
    contextHolder.setMediaBasePath(mediaBasePath);

    return contextHolder;

  }

  @Autowired
  @Qualifier(value = "privileges")
  private PluginRegistry<Privilege, String> privileges;

  @Bean
  public DefaultLastConnectionUpdateAuthenticationSuccessHandler lastConnectionUpdateAuthenticationSuccessHandler(
      UserService userService) {
    return new DefaultLastConnectionUpdateAuthenticationSuccessHandler(userService);
  }

  @Bean
  @Primary
  public UserDetailsService dbUserDetailsService(UserService userService, RoleService roleService,
      ResponsibilityService responsibilityService, MembershipService membershipService) {
    return new DefaultCurrentUserDetailsService(userService, roleService, responsibilityService,
        membershipService);
  }

}
