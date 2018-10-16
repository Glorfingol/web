package com.cmpl.web.configuration.manager.privileges;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WidgetPrivilegeConfiguration {

  @Bean
  public Privilege widgetsReadPrivilege() {
    return new SimplePrivilege("webmastering", "widgets", "read");
  }

  @Bean
  public Privilege widgetsWritePrivilege() {
    return new SimplePrivilege("webmastering", "widgets", "write");
  }

  @Bean
  public Privilege widgetsCreatePrivilege() {
    return new SimplePrivilege("webmastering", "widgets", "create");
  }

  @Bean
  public Privilege widgetsDeletePrivilege() {
    return new SimplePrivilege("webmastering", "widgets", "delete");
  }

  @Bean
  public Privilege widgetsPersonalizationWritePrivilege() {
    return new SimplePrivilege("webmastering", "widgets-personalization", "write");
  }
}
