package com.cmpl.web.configuration.manager.privileges;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;

@Configuration
public class WebsitePrivilegeConfiguration {

  @Bean
  public Privilege rolesReadPrivilege() {
    return new SimplePrivilege("webmastering", "websites", "read");
  }

  @Bean
  public Privilege rolesWritePrivilege() {
    return new SimplePrivilege("webmastering", "websites", "write");
  }

  @Bean
  public Privilege rolesCreatePrivilege() {
    return new SimplePrivilege("webmastering", "websites", "create");
  }

  @Bean
  public Privilege rolesDeletePrivilege() {
    return new SimplePrivilege("webmastering", "websites", "delete");
  }
}
