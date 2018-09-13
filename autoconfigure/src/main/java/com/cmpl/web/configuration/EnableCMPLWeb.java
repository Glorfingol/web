package com.cmpl.web.configuration;

import java.lang.annotation.*;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@Import(EnableCMPLWebImportSelector.class)
public @interface EnableCMPLWeb {

  Class<?>[] exclude() default {};

  String[] excludeName() default {};

}
