package com.cmpl.web.configuration.manager.backpages;

import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StyleBackPageConfiguration {

  @Bean
  BackPage viewStyles() {
    return BackPageBuilder.create().decorated(true).pageName("STYLE_VIEW")
        .templatePath("back/styles/view_styles").titleKey("back.style.title").build();
  }

  @Bean
  BackPage createStyle() {
    return BackPageBuilder.create().decorated(true).pageName("STYLE_CREATE")
        .templatePath("back/styles/create_style").titleKey("back.style.title").build();
  }

  @Bean
  BackPage updateStyle() {
    return BackPageBuilder.create().decorated(true).pageName("STYLE_UPDATE")
        .templatePath("back/styles/edit_style").titleKey("back.style.title").build();
  }

}
