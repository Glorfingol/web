package com.cmpl.web.launcher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.cmpl.web.configuration.EnableCMPLWeb;
import com.cmpl.web.core.carousel.CarouselItemRepository;
import com.cmpl.web.core.carousel.CarouselRepository;
import com.cmpl.web.core.media.MediaRepository;
import com.cmpl.web.core.menu.MenuRepository;
import com.cmpl.web.core.meta.MetaElementRepository;
import com.cmpl.web.core.meta.OpenGraphMetaElementRepository;
import com.cmpl.web.core.news.NewsContentRepository;
import com.cmpl.web.core.news.NewsEntryRepository;
import com.cmpl.web.core.page.PageRepository;

/**
 * Main du projet, lance une application springboot
 * 
 * @author Louis
 * */
@SpringBootApplication
@EnableCMPLWeb
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
  public CommandLineRunner init(final NewsEntryRepository newsEntryRepository,
      final NewsContentRepository newsContentRepository, final PageRepository pageRepository,
      final MetaElementRepository metaElementRepository,
      final OpenGraphMetaElementRepository openGraphMetaElementRepository, final MenuRepository menuRepository,
      final CarouselRepository carouselRepository, final CarouselItemRepository carouselItemRepository,
      final MediaRepository mediaRepository) {
    return (args) -> {

      NewsFactory.createNewsEntries(newsEntryRepository, newsContentRepository);

      PageFactory.createPages(pageRepository, menuRepository, metaElementRepository, openGraphMetaElementRepository,
          carouselRepository, carouselItemRepository, mediaRepository);

    };
  }

}
