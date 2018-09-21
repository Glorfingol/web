package com.cmpl.web.configuration.manager.privileges;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagePrivilegeConfiguration {

  @Bean
  public Privilege pagesReadPrivilege() {
    return new SimplePrivilege("webmastering", "pages", "read");
  }

  @Bean
  public Privilege pagesWritePrivilege() {
    return new SimplePrivilege("webmastering", "pages", "write");
  }

  @Bean
  public Privilege pagesCreatePrivilege() {
    return new SimplePrivilege("webmastering", "pages", "create");
  }

  @Bean
  public Privilege pagesDeletePrivilege() {
    return new SimplePrivilege("webmastering", "pages", "delete");
  }

}
