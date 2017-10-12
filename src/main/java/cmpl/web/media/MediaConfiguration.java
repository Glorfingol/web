package cmpl.web.media;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.file.FileService;
import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;

@Configuration
public class MediaConfiguration {

  @Bean
  public MediaService mediaService(MediaRepository mediaRepository, FileService fileService, ContextHolder contextHolder) {
    return new MediaServiceImpl(mediaRepository, fileService, contextHolder);
  }

  @Bean
  public MediaManagerDisplayFactory mediaManagerDisplayFactory(MenuFactory menuFactory,
      WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory, MediaService mediaService,
      ContextHolder contextHolder) {
    return new MediaManagerDisplayFactoryImpl(menuFactory, messageSource, metaElementFactory, mediaService,
        contextHolder);
  }

}
