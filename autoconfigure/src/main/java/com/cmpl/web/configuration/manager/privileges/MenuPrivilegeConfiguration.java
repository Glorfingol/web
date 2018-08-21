package com.cmpl.web.configuration.manager.privileges;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuPrivilegeConfiguration {

  @Bean
  public Privilege menuReadPrivilege() {
    return new SimplePrivilege("webmastering", "menu", "read");
  }

  @Bean
  public Privilege menuWritePrivilege() {
    return new SimplePrivilege("webmastering", "menu", "write");
  }

  @Bean
  public Privilege menuCreatePrivilege() {
    return new SimplePrivilege("webmastering", "menu", "create");
  }

  @Bean
  public Privilege menuDeletePrivilege() {
    return new SimplePrivilege("webmastering", "menu", "delete");
  }

}
