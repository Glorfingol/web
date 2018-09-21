package com.cmpl.web.configuration.manager.backpages;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({UserBackPageConfiguration.class, RoleBackPageConfiguration.class,
    GroupBackPageConfiguration.class})
public class AdministrationBackPageConfiguration {

}
