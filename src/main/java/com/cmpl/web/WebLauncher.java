package com.cmpl.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import com.cmpl.web.carousel.CarouselItemRepository;
import com.cmpl.web.carousel.CarouselRepository;
import com.cmpl.web.media.MediaRepository;
import com.cmpl.web.menu.MenuRepository;
import com.cmpl.web.meta.MetaElementRepository;
import com.cmpl.web.meta.OpenGraphMetaElementRepository;
import com.cmpl.web.news.NewsContent;
import com.cmpl.web.news.NewsContentRepository;
import com.cmpl.web.news.NewsEntry;
import com.cmpl.web.news.NewsEntryRepository;
import com.cmpl.web.page.PageRepository;

/**
 * Main du projet, lance une application springboot
 * 
 * @author Louis
 *
 */
@SpringBootApplication
@EnableCaching
public class WebLauncher {

  /**
   * Main du projet, lance un SpringApplication
   * 
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(WebLauncher.class, args);
  }

  @Bean
  @Profile("dev")
  @Transactional
  public CommandLineRunner init(final NewsEntryRepository newsEntryRepository,
      final NewsContentRepository newsContentRepository, final PageRepository pageRepository,
      final MetaElementRepository metaElementRepository,
      final OpenGraphMetaElementRepository openGraphMetaElementRepository, final MenuRepository menuRepository,
      final CarouselRepository carouselRepository, final CarouselItemRepository carouselItemRepository,
      final MediaRepository mediaRepository) {
    return (args) -> {

      NewsContent newsContent = createNewsContent(newsContentRepository);

      newsEntryRepository.saveAll(createNewsEntries(newsContent));

      PageFactory.createPages(pageRepository, menuRepository, metaElementRepository, openGraphMetaElementRepository,
          carouselRepository, carouselItemRepository, mediaRepository);

    };
  }

  private List<NewsEntry> createNewsEntries(NewsContent newsContent) {

    List<NewsEntry> newsEntries = new ArrayList<>();

    NewsEntry newsEntryEmpty = new NewsEntry();
    newsEntryEmpty.setAuthor("TEST");
    newsEntryEmpty.setTitle("Un test vide");
    newsEntryEmpty.setTags("test;fun");

    NewsEntry newsEntryNotEmpty = new NewsEntry();
    newsEntryNotEmpty.setAuthor("TEST");
    newsEntryNotEmpty.setTitle("Un test avec contenu");
    newsEntryNotEmpty.setTags("test;fun;yolo");
    newsEntryNotEmpty.setContentId(String.valueOf(newsContent.getId()));

    NewsEntry newsEntryEmpty2 = new NewsEntry();
    newsEntryEmpty2.setAuthor("TEST");
    newsEntryEmpty2.setTitle("Un test vide 2");
    newsEntryEmpty2.setTags("test;fun");

    newsEntries.add(newsEntryEmpty);
    newsEntries.add(newsEntryNotEmpty);
    newsEntries.add(newsEntryEmpty2);

    return newsEntries;
  }

  private NewsContent createNewsContent(final NewsContentRepository newsContentRepository) {
    NewsContent newsContent = new NewsContent();
    newsContent.setContent("Un contenu de test");
    return newsContentRepository.save(newsContent);

  }

}
