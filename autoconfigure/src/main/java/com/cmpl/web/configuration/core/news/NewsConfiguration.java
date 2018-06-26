package com.cmpl.web.configuration.core.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.core.events_listeners.NewsEventsListeners;
import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.news.BlogEntryWidgetProvider;
import com.cmpl.web.core.factory.news.BlogWidgetProvider;
import com.cmpl.web.core.factory.news.NewsManagerDisplayFactory;
import com.cmpl.web.core.factory.news.NewsManagerDisplayFactoryImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.news.content.NewsContent;
import com.cmpl.web.core.news.content.NewsContentDAO;
import com.cmpl.web.core.news.content.NewsContentDAOImpl;
import com.cmpl.web.core.news.content.NewsContentMapper;
import com.cmpl.web.core.news.content.NewsContentRepository;
import com.cmpl.web.core.news.content.NewsContentService;
import com.cmpl.web.core.news.content.NewsContentServiceImpl;
import com.cmpl.web.core.news.entry.NewsEntry;
import com.cmpl.web.core.news.entry.NewsEntryDAO;
import com.cmpl.web.core.news.entry.NewsEntryDAOImpl;
import com.cmpl.web.core.news.entry.NewsEntryDispatcher;
import com.cmpl.web.core.news.entry.NewsEntryDispatcherImpl;
import com.cmpl.web.core.news.entry.NewsEntryMapper;
import com.cmpl.web.core.news.entry.NewsEntryRepository;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.news.entry.NewsEntryServiceImpl;
import com.cmpl.web.core.news.entry.NewsEntryTranslator;
import com.cmpl.web.core.news.entry.NewsEntryTranslatorImpl;
import com.cmpl.web.core.news.image.NewsImage;
import com.cmpl.web.core.news.image.NewsImageDAO;
import com.cmpl.web.core.news.image.NewsImageDAOImpl;
import com.cmpl.web.core.news.image.NewsImageMapper;
import com.cmpl.web.core.news.image.NewsImageRepository;
import com.cmpl.web.core.news.image.NewsImageService;
import com.cmpl.web.core.news.image.NewsImageServiceImpl;
import com.cmpl.web.core.page.BACK_PAGE;

@Configuration
@EntityScan(basePackageClasses = {NewsEntry.class, NewsContent.class, NewsImage.class})
@EnableJpaRepositories(basePackageClasses = {NewsEntryRepository.class, NewsImageRepository.class,
    NewsContentRepository.class})
public class NewsConfiguration {

  @Bean
  public NewsEntryDispatcher newsEntryDispatcher(NewsEntryTranslator translator, NewsEntryService newsEntryService,
      FileService fileService, MediaService mediaService) {
    return new NewsEntryDispatcherImpl(translator, newsEntryService, fileService, mediaService);
  }

  @Bean
  public BackMenuItem newsBackMenuItem(BackMenuItem webmastering, Privilege newsReadPrivilege) {
    return BackMenuItemBuilder.create().href("back.news.href").label("back.news.label").title("back.news.title")
        .order(6).iconClass("fa fa-newspaper-o").parent(webmastering).privilege(newsReadPrivilege.privilege()).build();
  }

  @Bean
  public BreadCrumb newsViewBreadCrumb() {
    return BreadCrumbBuilder.create().items(newsBreadCrumbItems()).page(BACK_PAGE.NEWS_VIEW).build();
  }

  @Bean
  public BreadCrumb newsEditBreadCrumb() {
    return BreadCrumbBuilder.create().items(newsUpdateBreadCrumbItems()).page(BACK_PAGE.NEWS_UPDATE).build();
  }

  @Bean
  public BreadCrumb newsCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(newsBreadCrumbItems()).page(BACK_PAGE.NEWS_CREATE).build();
  }

  List<BreadCrumbItem> newsBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.news.title").href("back.news.href").build());
    return items;
  }

  List<BreadCrumbItem> newsUpdateBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.news.title").href("back.news.href").build());
    items.add(BreadCrumbItemBuilder.create().text("news.update.title").href("#").build());
    return items;
  }

  List<BreadCrumbItem> newsCreateBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.news.title").href("back.news.href").build());
    items.add(BreadCrumbItemBuilder.create().text("news.create.title").href("#").build());
    return items;
  }

  @Bean
  public NewsManagerDisplayFactory newsManagerDisplayFactory(ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, NewsEntryService newsEntryService,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs, Set<Locale> availableLocales) {
    return new NewsManagerDisplayFactoryImpl(contextHolder, menuFactory, messageSource, newsEntryService, breadCrumbs,
        availableLocales);
  }

  @Bean
  public NewsEntryTranslator newsEntryTranslator() {
    return new NewsEntryTranslatorImpl();
  }

  @Bean
  public NewsEntryDAO newsEntryDAO(NewsEntryRepository newsEntryRepository, ApplicationEventPublisher publisher) {
    return new NewsEntryDAOImpl(newsEntryRepository, publisher);
  }

  @Bean
  public NewsEntryMapper newsEntryMapper(NewsContentService newsContentService, NewsImageService newsImageService) {
    return new NewsEntryMapper(newsContentService, newsImageService);
  }

  @Bean
  public NewsEntryService newsEntryService(NewsEntryDAO newsEntryDAO, NewsEntryMapper newsEntryMapper,
      NewsImageService newsImageService, NewsContentService newsContentService) {
    return new NewsEntryServiceImpl(newsEntryDAO, newsImageService, newsContentService, newsEntryMapper);
  }

  @Bean
  public NewsImageDAO newsImageDAO(ApplicationEventPublisher publisher, NewsImageRepository newsImageRepository) {
    return new NewsImageDAOImpl(newsImageRepository, publisher);
  }

  @Bean
  public NewsImageMapper newsImageMapper(MediaService mediaService) {
    return new NewsImageMapper(mediaService);
  }

  @Bean
  public NewsImageService newsImageService(NewsImageDAO newsImageDAO, NewsImageMapper newsImageMapper) {
    return new NewsImageServiceImpl(newsImageDAO, newsImageMapper);
  }

  @Bean
  public NewsContentDAO newsContentDAO(ApplicationEventPublisher publisher,
      NewsContentRepository newsContentRepository) {
    return new NewsContentDAOImpl(newsContentRepository, publisher);
  }

  @Bean
  public NewsContentMapper newsContentMapper() {
    return new NewsContentMapper();
  }

  @Bean
  public NewsContentService newsContentService(NewsContentDAO newsContentDAO, NewsContentMapper newsContentMapper) {
    return new NewsContentServiceImpl(newsContentDAO, newsContentMapper);
  }

  @Bean
  public BlogWidgetProvider blogWidgetProvider(WebMessageSource messageSource, ContextHolder contextHolder,
      NewsEntryService newsEntryService) {
    return new BlogWidgetProvider(messageSource, contextHolder, newsEntryService);

  }

  @Bean
  public BlogEntryWidgetProvider blogEntryWidgetProvider(NewsEntryService newsEntryService) {
    return new BlogEntryWidgetProvider(newsEntryService);
  }

  @Bean
  public NewsEventsListeners newsEventsListener(NewsContentService newsContentService,
      NewsImageService newsImageService, FileService fileService) {
    return new NewsEventsListeners(newsContentService, newsImageService, fileService);
  }
}
