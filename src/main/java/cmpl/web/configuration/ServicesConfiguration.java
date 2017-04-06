package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.repository.NewsContentRepository;
import cmpl.web.repository.NewsEntryRepository;
import cmpl.web.repository.NewsImageRepository;
import cmpl.web.service.ImageConverterService;
import cmpl.web.service.NewsContentService;
import cmpl.web.service.NewsEntryService;
import cmpl.web.service.NewsImageService;
import cmpl.web.service.impl.ImageConverterServiceImpl;
import cmpl.web.service.impl.NewsContentServiceImpl;
import cmpl.web.service.impl.NewsEntryServiceImpl;
import cmpl.web.service.impl.NewsImageServiceImpl;

@Configuration
public class ServicesConfiguration {

  @Bean
  NewsEntryService newsEntryService(NewsEntryRepository newsEntryRepository, NewsImageService newsImageService,
      NewsContentService newsContentService, ImageConverterService imageConverterService) {
    return NewsEntryServiceImpl.fromRepositoriesAndServices(newsEntryRepository, newsImageService, newsContentService,
        imageConverterService);
  }

  @Bean
  NewsImageService newsImageService(NewsImageRepository newsImageRepository) {
    return NewsImageServiceImpl.fromRepositories(newsImageRepository);
  }

  @Bean
  NewsContentService newsContentService(NewsContentRepository newsContentRepository) {
    return NewsContentServiceImpl.fromRepositories(newsContentRepository);
  }

  @Bean
  ImageConverterService imageConverterService() {
    return ImageConverterServiceImpl.fromVoid();
  }
}
