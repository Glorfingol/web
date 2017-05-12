package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.message.WebMessageSource;
import cmpl.web.repository.NewsContentRepository;
import cmpl.web.repository.NewsEntryRepository;
import cmpl.web.repository.NewsImageRepository;
import cmpl.web.service.FileService;
import cmpl.web.service.ImageConverterService;
import cmpl.web.service.NewsContentService;
import cmpl.web.service.NewsEntryService;
import cmpl.web.service.NewsImageService;
import cmpl.web.service.SitemapService;
import cmpl.web.service.impl.FileServiceImpl;
import cmpl.web.service.impl.ImageConverterServiceImpl;
import cmpl.web.service.impl.NewsContentServiceImpl;
import cmpl.web.service.impl.NewsEntryServiceImpl;
import cmpl.web.service.impl.NewsImageServiceImpl;
import cmpl.web.service.impl.SitemapServiceImpl;

@Configuration
public class ServicesConfiguration {

  @Bean
  NewsEntryService newsEntryService(NewsEntryRepository newsEntryRepository, NewsImageService newsImageService,
      NewsContentService newsContentService, ImageConverterService imageConverterService, FileService fileService) {
    return NewsEntryServiceImpl.fromRepositoriesAndServices(newsEntryRepository, newsImageService, newsContentService,
        imageConverterService, fileService);
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

  @Bean
  SitemapService sitemapService(NewsEntryService newsEntryService, WebMessageSource messageSource) {
    return SitemapServiceImpl.fromService(newsEntryService, messageSource);
  }

  @Bean
  FileService fileService(ImageConverterService imageConverterService) {
    return FileServiceImpl.fromService(imageConverterService);
  }
}
