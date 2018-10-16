package com.cmpl.web.configuration.manager.privileges;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebsitePrivilegeConfiguration {

  @Bean
  public Privilege websitesReadPrivilege() {
    return new SimplePrivilege("webmastering", "websites", "read");
  }

  @Bean
  public Privilege websitesWritePrivilege() {
    return new SimplePrivilege("webmastering", "websites", "write");
  }

  @Bean
  public Privilege websitesCreatePrivilege() {
    return new SimplePrivilege("webmastering", "websites", "create");
  }

  @Bean
  public Privilege websitesDeletePrivilege() {
    return new SimplePrivilege("webmastering", "websites", "delete");
  }

}
