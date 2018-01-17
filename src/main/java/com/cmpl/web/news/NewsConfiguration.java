package com.cmpl.web.news;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.file.FileService;
import com.cmpl.web.file.ImageConverterService;
import com.cmpl.web.file.ImageService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;

@Configuration
public class NewsConfiguration {

  @Bean
  NewsEntryDispatcher newsEntryDispatcher(NewsEntryRequestValidator validator, NewsEntryTranslator translator,
      NewsEntryService newsEntryService, FileService fileService) {
    return new NewsEntryDispatcherImpl(validator, translator, newsEntryService, fileService);
  }

  @Bean
  BackMenuItem newsBackMenuItem() {
    return BackMenuItemBuilder.create().href("back.news.href").label("back.news.label").title("back.news.title")
        .order(6).iconClass("fa fa-newspaper-o").build();
  }

  @Bean
  BreadCrumb newsViewBreadCrumb() {
    return BreadCrumbBuilder.create().items(newsBreadCrumbItems()).page(BACK_PAGE.NEWS_VIEW).build();
  }

  @Bean
  BreadCrumb newsEditBreadCrumb() {
    return BreadCrumbBuilder.create().items(newsUpdateBreadCrumbItems()).page(BACK_PAGE.NEWS_UPDATE).build();
  }

  @Bean
  BreadCrumb newsCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(newsBreadCrumbItems()).page(BACK_PAGE.NEWS_CREATE).build();
  }

  @Bean
  BreadCrumb newsTemplateBreadCrumb() {
    return BreadCrumbBuilder.create().items(newsBreadCrumbItems()).page(BACK_PAGE.NEWS_TEMPLATE).build();
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
    items.add(BreadCrumbItemBuilder.create().text("Modifier une entrée").href("/manager/actualite/modifier/").build());
    return items;
  }

  List<BreadCrumbItem> newsCreateBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.news.title").href("back.news.href").build());
    items.add(BreadCrumbItemBuilder.create().text("news.create.title").href("news.create.href").build());
    return items;
  }

  @Bean
  NewsManagerDisplayFactory newsManagerDisplayFactory(ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, NewsEntryService newsEntryService, FileService fileService,
      @Qualifier(value = "breadCrumbs") PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry) {
    return new NewsManagerDisplayFactoryImpl(contextHolder, menuFactory, messageSource, newsEntryService, fileService,
        breadCrumbRegistry);
  }

  @Bean
  NewsEntryTranslator newsEntryTranslator() {
    return new NewsEntryTranslatorImpl();
  }

  @Bean
  NewsEntryRequestValidator newsEntryRequestValidator(WebMessageSource messageSource) {
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
