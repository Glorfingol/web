package com.cmpl.web.configuration.core.widget;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;

@Configuration
public class WidgetPrivilegeConfiguration {

  @Bean
  public Privilege widgetReadPrivilege() {
    return new SimplePrivilege("webmastering", "widget", "read");
  }

  @Bean
  public Privilege widgetWritePrivilege() {
    return new SimplePrivilege("webmastering", "widget", "write");
  }

  @Bean
  public Privilege widgetCreatePrivilege() {
    return new SimplePrivilege("webmastering", "widget", "create");
  }

  @Bean
  public Privilege widgetDeletePrivilege() {
    return new SimplePrivilege("webmastering", "widget", "delete");
  }
}
