package com.cmpl.web.configuration.manager.privileges;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StylePrivilegeConfiguration {

  @Bean
  public Privilege styleReadPrivilege() {
    return new SimplePrivilege("webmastering", "style", "read");
  }

  @Bean
  public Privilege styleWritePrivilege() {
    return new SimplePrivilege("webmastering", "style", "write");
  }

  @Bean
  public Privilege styleCreatePrivilege() {
    return new SimplePrivilege("webmastering", "style", "create");
  }

  @Bean
  public Privilege styleDeletePrivilege() {
    return new SimplePrivilege("webmastering", "style", "delete");
  }

}
