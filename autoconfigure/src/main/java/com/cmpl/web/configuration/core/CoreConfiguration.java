package com.cmpl.web.configuration.core;

import com.cmpl.web.configuration.core.common.CommonConfiguration;
import com.cmpl.web.configuration.core.scheduler.SchedulerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CommonConfiguration.class, AdministrationConfiguration.class,
    WebmasteringConfiguration.class,
    SchedulerConfiguration.class, FileConfiguration.class})
public class CoreConfiguration {

}
