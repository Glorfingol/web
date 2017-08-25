package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.facebook.FacebookImportService;
import cmpl.web.facebook.FacebookImportServiceImpl;
import cmpl.web.facebook.FacebookService;
import cmpl.web.facebook.FacebookServiceImpl;
import cmpl.web.file.FileService;
import cmpl.web.file.FileServiceImpl;
import cmpl.web.file.ImageConverterService;
import cmpl.web.file.ImageConverterServiceImpl;
import cmpl.web.menu.MenuRepository;
import cmpl.web.menu.MenuService;
import cmpl.web.menu.MenuServiceImpl;
import cmpl.web.message.WebMessageSource;
import cmpl.web.meta.MetaElementRepository;
import cmpl.web.meta.MetaElementService;
import cmpl.web.meta.MetaElementServiceImpl;
import cmpl.web.meta.OpenGraphMetaElementRepository;
import cmpl.web.meta.OpenGraphMetaElementService;
import cmpl.web.meta.OpenGraphMetaElementServiceImpl;
import cmpl.web.news.NewsContentRepository;
import cmpl.web.news.NewsContentService;
import cmpl.web.news.NewsContentServiceImpl;
import cmpl.web.news.NewsEntryRepository;
import cmpl.web.news.NewsEntryService;
import cmpl.web.news.NewsEntryServiceImpl;
import cmpl.web.news.NewsImageRepository;
import cmpl.web.news.NewsImageService;
import cmpl.web.news.NewsImageServiceImpl;
import cmpl.web.page.PageRepository;
import cmpl.web.page.PageService;
import cmpl.web.page.PageServiceImpl;
import cmpl.web.sitemap.SitemapService;
import cmpl.web.sitemap.SitemapServiceImpl;

/**
 * Configuration des services
 * 
 * @author Louis
 *
 */
@Configuration
public class ServicesConfiguration {

  @Bean
  NewsEntryService newsEntryService(NewsEntryRepository newsEntryRepository, NewsImageService newsImageService,
      NewsContentService newsContentService, ImageConverterService imageConverterService, FileService fileService) {
    return new NewsEntryServiceImpl(newsEntryRepository, newsImageService, newsContentService, imageConverterService,
        fileService);
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
    return new SitemapServiceImpl(newsEntryService, messageSource);
  }

  @Bean
  FileService fileService(ContextHolder contextHolder, ImageConverterService imageConverterService) {
    return FileServiceImpl.fromStringAndService(contextHolder, imageConverterService);
  }

  @Bean
  FacebookService facebookService(ContextHolder contextHolder, Facebook facebookConnector,
      ConnectionRepository connectionRepository, NewsEntryService newsEntryService) {
    return FacebookServiceImpl.fromFacebookConnector(contextHolder, facebookConnector, connectionRepository,
        newsEntryService);
  }

  @Bean
  FacebookImportService facebookImportService(NewsEntryService newsEntryService, Facebook facebookConnector,
      WebMessageSource messageSource) {
    return FacebookImportServiceImpl.fromService(newsEntryService, facebookConnector, messageSource);
  }

  @Bean
  PageService pageService(PageRepository pageRepository, MetaElementService metaElementService,
      OpenGraphMetaElementService openGraphMetaElementService) {
    return PageServiceImpl.fromRepository(pageRepository, metaElementService, openGraphMetaElementService);
  }

  @Bean
  MetaElementService metaElementService(MetaElementRepository repository) {
    return MetaElementServiceImpl.fromRepository(repository);
  }

  @Bean
  OpenGraphMetaElementService openGraphMetaElementService(OpenGraphMetaElementRepository repository) {
    return OpenGraphMetaElementServiceImpl.fromRepository(repository);
  }

  @Bean
  MenuService menuService(MenuRepository menuRepository) {
    return MenuServiceImpl.fromRepository(menuRepository);
  }
}
