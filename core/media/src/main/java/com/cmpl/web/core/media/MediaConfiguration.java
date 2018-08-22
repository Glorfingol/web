package com.cmpl.web.core.media;

import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.models.Media;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = Media.class)
@EnableJpaRepositories(basePackageClasses = MediaRepository.class)
public class MediaConfiguration {

  @Bean
  public MediaDAO mediaDAO(MediaRepository mediaRepository, ApplicationEventPublisher publisher) {
    return new DefaultMediaDAO(mediaRepository, publisher);
  }

  @Bean
  public MediaMapper mediaMapper() {
    return new MediaMapper();
  }

  @Bean
  public MediaService mediaService(MediaDAO mediaDAO, MediaMapper mediaMapper,
      FileService fileService) {
    return new DefaultMediaService(mediaDAO, mediaMapper, fileService);
  }

}
