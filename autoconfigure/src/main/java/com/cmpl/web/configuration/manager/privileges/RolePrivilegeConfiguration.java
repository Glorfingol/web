package com.cmpl.web.configuration.manager.privileges;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RolePrivilegeConfiguration {

  @Bean
  public Privilege rolesReadPrivilege() {
    return new SimplePrivilege("administration", "roles", "read");
  }

  @Bean
  public Privilege rolesWritePrivilege() {
    return new SimplePrivilege("administration", "roles", "write");
  }

  @Bean
  public Privilege rolesCreatePrivilege() {
    return new SimplePrivilege("administration", "roles", "create");
  }

  @Bean
  public Privilege rolesDeletePrivilege() {
    return new SimplePrivilege("administration", "roles", "delete");
  }

  @Bean
  public Privilege rolesPrivilegesWritePrivilege() {
    return new SimplePrivilege("administration", "roles-privileges", "write");
  }

  @Bean
  public Privilege rolesPrivilegesReadPrivilege() {
    return new SimplePrivilege("administration", "roles-privileges", "read");
  }

}
