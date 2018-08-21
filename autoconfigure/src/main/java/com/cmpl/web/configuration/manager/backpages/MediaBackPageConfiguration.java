package com.cmpl.web.configuration.manager.backpages;

import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MediaBackPageConfiguration {

  @Bean
  BackPage viewMedias() {
    return BackPageBuilder.create().decorated(true).pageName("MEDIA_VIEW")
        .templatePath("back/medias/view_medias").titleKey("back.medias.title").build();
  }

  @Bean
  BackPage createMedia() {
    return BackPageBuilder.create().decorated(true).pageName("MEDIA_CREATE")
        .templatePath("back/medias/upload_media").titleKey("back.medias.title").build();
  }

  @Bean
  BackPage updateMedia() {
    return BackPageBuilder.create().decorated(true).pageName("MEDIA_UPDATE")
        .templatePath("back/medias/view_media").titleKey("back.medias.title").build();
  }

}
