package com.cmpl.web.configuration.manager.backpages;

import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageBackPageConfiguration {

  @Bean
  BackPage viewPages() {
    return BackPageBuilder.create().decorated(true).pageName("PAGE_VIEW")
        .templatePath("back/pages/view_pages").titleKey("back.pages.title").build();
  }

  @Bean
  BackPage createPage() {
    return BackPageBuilder.create().decorated(true).pageName("PAGE_CREATE")
        .templatePath("back/pages/create_page").titleKey("back.pages.title").build();
  }

  @Bean
  BackPage updatePage() {
    return BackPageBuilder.create().decorated(true).pageName("PAGE_UPDATE")
        .templatePath("back/pages/edit_page").titleKey("back.pages.title").build();
  }

}
