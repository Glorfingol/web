package com.cmpl.web.configuration.manager.backpages;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CarouselBackPageConfiguration.class, MediaBackPageConfiguration.class,
    NewsBackPageConfiguration.class,
    PageBackPageConfiguration.class, StyleBackPageConfiguration.class,
    WebsiteBackPageConfiguration.class, WidgetBackPageConfiguration.class})
public class WebMasteringBackPageConfiguration {

}
