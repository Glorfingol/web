package com.cmpl.web.configuration.core;

import com.cmpl.web.core.group.GroupConfiguration;
import com.cmpl.web.core.membership.MembershipConfiguration;
import com.cmpl.web.core.responsibility.ResponsibilityConfiguration;
import com.cmpl.web.core.role.RoleConfiguration;
import com.cmpl.web.core.user.UserConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({UserConfiguration.class, RoleConfiguration.class, MembershipConfiguration.class,
    GroupConfiguration.class,
    ResponsibilityConfiguration.class})

public class AdministrationConfiguration {

}
