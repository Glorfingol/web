package com.cmpl.web.configuration.core.scheduler;

import com.cmpl.web.backup.AutowiringSpringBeanJobFactory;
import org.quartz.spi.JobFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/scheduler/scheduler.properties")
public class SchedulerConfiguration {

  @Bean
  public JobFactory jobFactory(ApplicationContext applicationContext) {
    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }


}
