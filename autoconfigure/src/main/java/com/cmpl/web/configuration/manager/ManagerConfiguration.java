package com.cmpl.web.configuration.manager;

import com.cmpl.web.configuration.manager.backpages.BackPagesConfiguration;
import com.cmpl.web.configuration.manager.breadcrumbs.BreadCrumbConfiguration;
import com.cmpl.web.configuration.manager.display.DisplayFactoryConfiguration;
import com.cmpl.web.configuration.manager.display.WidgetProviderConfiguration;
import com.cmpl.web.configuration.manager.privileges.PrivilegesConfiguration;
import com.cmpl.web.configuration.manager.ui.BackControllerConfiguration;
import com.cmpl.web.configuration.manager.ui.ManagerMenuConfiguration;
import com.cmpl.web.configuration.manager.ui.NotificationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BackControllerConfiguration.class, DisplayFactoryConfiguration.class,
    ManagerMenuConfiguration.class, WidgetProviderConfiguration.class,
    BreadCrumbConfiguration.class, BackPagesConfiguration.class, NotificationConfiguration.class,
    PrivilegesConfiguration.class})
public class ManagerConfiguration {

}
