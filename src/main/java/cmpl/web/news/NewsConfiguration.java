package cmpl.web.news;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.file.ImageConverterService;
import cmpl.web.file.ImageService;
import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;

@Configuration
public class NewsConfiguration {

  @Bean
  NewsEntryDispatcher newsEntryDispatcher(NewsEntryRequestValidator validator, NewsEntryTranslator translator,
      NewsEntryService newsEntryService) {
    return new NewsEntryDispatcherImpl(validator, translator, newsEntryService);
  }

  @Bean
  NewsManagerDisplayFactory newsManagerDisplayFactory(ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSourceImpl messageSource, NewsEntryService newsEntryService, MetaElementFactory metaElementFactory) {
    return new NewsManagerDisplayFactoryImpl(contextHolder, menuFactory, messageSource, newsEntryService,
        metaElementFactory);
  }

  @Bean
  NewsEntryTranslator newsEntryTranslator() {
    return new NewsEntryTranslatorImpl();
  }

  @Bean
  NewsEntryRequestValidator newsEntryRequestValidator(WebMessageSourceImpl messageSource) {
    return new NewsEntryRequestValidatorImpl(messageSource);
  }

  @Bean
  NewsEntryService newsEntryService(NewsEntryRepository newsEntryRepository, NewsImageService newsImageService,
      NewsContentService newsContentService, ImageConverterService imageConverterService, ImageService imageService) {
    return new NewsEntryServiceImpl(newsEntryRepository, newsImageService, newsContentService, imageConverterService,
        imageService);
  }

  @Bean
  NewsImageService newsImageService(NewsImageRepository newsImageRepository) {
    return new NewsImageServiceImpl(newsImageRepository);
  }

  @Bean
  NewsContentService newsContentService(NewsContentRepository newsContentRepository) {
    return new NewsContentServiceImpl(newsContentRepository);
  }

}
