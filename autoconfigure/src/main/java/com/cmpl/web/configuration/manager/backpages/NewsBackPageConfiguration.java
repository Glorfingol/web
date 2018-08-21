package com.cmpl.web.configuration.manager.backpages;

import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewsBackPageConfiguration {

  @Bean
  BackPage viewNews() {
    return BackPageBuilder.create().decorated(true).pageName("NEWS_VIEW")
        .templatePath("back/news/view_news").titleKey("back.news.title").build();
  }

  @Bean
  BackPage createNews() {
    return BackPageBuilder.create().decorated(true).pageName("NEWS_CREATE")
        .templatePath("back/news/create_news_entry").titleKey("back.news.title").build();
  }

  @Bean
  BackPage updateNews() {
    return BackPageBuilder.create().decorated(true).pageName("NEWS_UPDATE")
        .templatePath("back/news/edit_news_entry").titleKey("back.news.title").build();
  }

}
