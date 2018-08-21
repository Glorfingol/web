package com.cmpl.web.configuration.manager.privileges;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DesignPrivilegeConfiguration {

  @Bean
  public Privilege designsReadPrivilege() {
    return new SimplePrivilege("webmastering", "designs", "read");
  }

  @Bean
  public Privilege designsWritePrivilege() {
    return new SimplePrivilege("webmastering", "designs", "write");
  }

  @Bean
  public Privilege designsCreatePrivilege() {
    return new SimplePrivilege("webmastering", "designs", "create");
  }

  @Bean
  public Privilege designsDeletePrivilege() {
    return new SimplePrivilege("webmastering", "designs", "delete");
  }
}
