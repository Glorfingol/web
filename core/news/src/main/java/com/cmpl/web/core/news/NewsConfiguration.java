package com.cmpl.web.core.news;

import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.models.NewsContent;
import com.cmpl.web.core.models.NewsEntry;
import com.cmpl.web.core.models.NewsImage;
import com.cmpl.web.core.news.content.DefaultNewsContentDAO;
import com.cmpl.web.core.news.content.DefaultNewsContentService;
import com.cmpl.web.core.news.content.NewsContentDAO;
import com.cmpl.web.core.news.content.NewsContentMapper;
import com.cmpl.web.core.news.content.NewsContentRepository;
import com.cmpl.web.core.news.content.NewsContentService;
import com.cmpl.web.core.news.entry.DefaultNewsEntryDAO;
import com.cmpl.web.core.news.entry.DefaultNewsEntryDispatcher;
import com.cmpl.web.core.news.entry.DefaultNewsEntryService;
import com.cmpl.web.core.news.entry.DefaultNewsEntryTranslator;
import com.cmpl.web.core.news.entry.DefaultRenderingNewsService;
import com.cmpl.web.core.news.entry.NewsEntryDAO;
import com.cmpl.web.core.news.entry.NewsEntryDispatcher;
import com.cmpl.web.core.news.entry.NewsEntryMapper;
import com.cmpl.web.core.news.entry.NewsEntryRepository;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.news.entry.NewsEntryTranslator;
import com.cmpl.web.core.news.entry.RenderingNewsEntryMapper;
import com.cmpl.web.core.news.entry.RenderingNewsService;
import com.cmpl.web.core.news.image.DefaultNewsImageDAO;
import com.cmpl.web.core.news.image.DefaultNewsImageService;
import com.cmpl.web.core.news.image.NewsImageDAO;
import com.cmpl.web.core.news.image.NewsImageMapper;
import com.cmpl.web.core.news.image.NewsImageRepository;
import com.cmpl.web.core.news.image.NewsImageService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {NewsEntry.class, NewsContent.class, NewsImage.class})
@EnableJpaRepositories(basePackageClasses = {NewsEntryRepository.class, NewsImageRepository.class,
  NewsContentRepository.class})
public class NewsConfiguration {

  @Bean
  public NewsEntryDispatcher newsEntryDispatcher(NewsEntryTranslator translator,
    NewsEntryService newsEntryService,
    FileService fileService, MediaService mediaService) {
    return new DefaultNewsEntryDispatcher(translator, newsEntryService, fileService, mediaService);
  }

  @Bean
  public NewsEntryTranslator newsEntryTranslator() {
    return new DefaultNewsEntryTranslator();
  }

  @Bean
  public NewsEntryDAO newsEntryDAO(NewsEntryRepository newsEntryRepository,
    ApplicationEventPublisher publisher) {
    return new DefaultNewsEntryDAO(newsEntryRepository, publisher);
  }

  @Bean
  public NewsEntryMapper newsEntryMapper(NewsContentService newsContentService,
    NewsImageService newsImageService) {
    return new NewsEntryMapper(newsContentService, newsImageService);
  }

  @Bean
  public NewsEntryService newsEntryService(NewsEntryDAO newsEntryDAO,
    NewsEntryMapper newsEntryMapper,
    NewsImageService newsImageService, NewsContentService newsContentService) {
    return new DefaultNewsEntryService(newsEntryDAO, newsImageService, newsContentService,
      newsEntryMapper);
  }

  @Bean
  public NewsImageDAO newsImageDAO(ApplicationEventPublisher publisher,
    NewsImageRepository newsImageRepository) {
    return new DefaultNewsImageDAO(newsImageRepository, publisher);
  }

  @Bean
  public NewsImageMapper newsImageMapper(MediaService mediaService) {
    return new NewsImageMapper(mediaService);
  }

  @Bean
  public NewsImageService newsImageService(NewsImageDAO newsImageDAO,
    NewsImageMapper newsImageMapper) {
    return new DefaultNewsImageService(newsImageDAO, newsImageMapper);
  }

  @Bean
  public NewsContentDAO newsContentDAO(ApplicationEventPublisher publisher,
    NewsContentRepository newsContentRepository) {
    return new DefaultNewsContentDAO(newsContentRepository, publisher);
  }

  @Bean
  public NewsContentMapper newsContentMapper() {
    return new NewsContentMapper();
  }

  @Bean
  public NewsContentService newsContentService(NewsContentDAO newsContentDAO,
    NewsContentMapper newsContentMapper, FileService fileService) {
    return new DefaultNewsContentService(newsContentDAO, newsContentMapper, fileService);
  }

  @Bean
  public RenderingNewsEntryMapper renderingNewsEntryMapper(NewsContentService newsContentService,
    NewsImageService newsImageService) {
    return new RenderingNewsEntryMapper(newsContentService, newsImageService);
  }

  @Bean
  public RenderingNewsService renderingNewsService(NewsEntryDAO newsEntryDAO,
    RenderingNewsEntryMapper renderingNewsEntryMapper) {
    return new DefaultRenderingNewsService(newsEntryDAO, renderingNewsEntryMapper);
  }

}
