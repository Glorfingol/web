package com.cmpl.web.configuration.manager.backpages;

import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarouselBackPageConfiguration {

  @Bean
  BackPage viewCarousels() {
    return BackPageBuilder.create().decorated(true).pageName("CAROUSEL_VIEW")
        .templatePath("back/carousels/view_carousels").titleKey("back.carousels.title").build();
  }

  @Bean
  BackPage createCarousel() {
    return BackPageBuilder.create().decorated(true).pageName("CAROUSEL_CREATE")
        .templatePath("back/carousels/create_carousel").titleKey("back.carousels.title").build();
  }

  @Bean
  BackPage updateCarousel() {
    return BackPageBuilder.create().decorated(true).pageName("CAROUSEL_UPDATE")
        .templatePath("back/carousels/edit_carousel").titleKey("back.carousels.title").build();
  }

}
