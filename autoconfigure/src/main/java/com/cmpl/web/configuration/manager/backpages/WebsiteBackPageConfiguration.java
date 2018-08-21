package com.cmpl.web.configuration.manager.backpages;

import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebsiteBackPageConfiguration {

  @Bean
  BackPage viewWebsites() {
    return BackPageBuilder.create().decorated(true).pageName("WEBSITE_VIEW")
        .templatePath("back/websites/view_websites").titleKey("back.websites.title").build();
  }

  @Bean
  BackPage createWebsite() {
    return BackPageBuilder.create().decorated(true).pageName("WEBSITE_CREATE")
        .templatePath("back/websites/create_website").titleKey("back.websites.title").build();
  }

  @Bean
  BackPage updateWebsite() {
    return BackPageBuilder.create().decorated(true).pageName("WEBSITE_UPDATE")
        .templatePath("back/websites/edit_website").titleKey("back.websites.title").build();
  }

}
