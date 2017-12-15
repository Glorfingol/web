package com.cmpl.web.backup;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.cmpl.web.carousel.Carousel;
import com.cmpl.web.carousel.CarouselItem;
import com.cmpl.web.carousel.CarouselItemRepository;
import com.cmpl.web.carousel.CarouselRepository;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.media.Media;
import com.cmpl.web.media.MediaRepository;
import com.cmpl.web.menu.Menu;
import com.cmpl.web.menu.MenuRepository;
import com.cmpl.web.meta.MetaElement;
import com.cmpl.web.meta.MetaElementRepository;
import com.cmpl.web.meta.OpenGraphMetaElement;
import com.cmpl.web.meta.OpenGraphMetaElementRepository;
import com.cmpl.web.news.NewsContent;
import com.cmpl.web.news.NewsContentRepository;
import com.cmpl.web.news.NewsEntry;
import com.cmpl.web.news.NewsEntryRepository;
import com.cmpl.web.news.NewsImage;
import com.cmpl.web.news.NewsImageRepository;
import com.cmpl.web.page.Page;
import com.cmpl.web.page.PageRepository;
import com.cmpl.web.style.Style;
import com.cmpl.web.style.StyleRepository;

@Configuration
@PropertySource("classpath:/backup/backup.properties")
public class BackupConfiguration {

  @Bean
  public DataManipulator<Menu> menuDataManipulator(MenuRepository menuRepository) {
    return new DataManipulator<>(menuRepository);
  }

  @Bean
  public DataManipulator<Style> styleDataManipulator(StyleRepository styleRepository) {
    return new DataManipulator<>(styleRepository);
  }

  @Bean
  public DataManipulator<Page> pageDataManipulator(PageRepository pageRepository) {
    return new DataManipulator<>(pageRepository);
  }

  @Bean
  public DataManipulator<Media> mediaDataManipulator(MediaRepository mediaRepository) {
    return new DataManipulator<>(mediaRepository);
  }

  @Bean
  public DataManipulator<Carousel> carouselDataManipulator(CarouselRepository carouselRepository) {
    return new DataManipulator<>(carouselRepository);
  }

  @Bean
  public DataManipulator<CarouselItem> carouselItemDataManipulator(CarouselItemRepository carouselItemRepository) {
    return new DataManipulator<>(carouselItemRepository);
  }

  @Bean
  public DataManipulator<MetaElement> metaElementDataManipulator(MetaElementRepository metaElementRepository) {
    return new DataManipulator<>(metaElementRepository);
  }

  @Bean
  public DataManipulator<OpenGraphMetaElement> openGraphMetaElementDataManipulator(
      OpenGraphMetaElementRepository openGraphMetaElementRepository) {
    return new DataManipulator<>(openGraphMetaElementRepository);
  }

  @Bean
  public DataManipulator<NewsEntry> newsEntryDataManipulator(NewsEntryRepository newsEntryRepository) {
    return new DataManipulator<>(newsEntryRepository);
  }

  @Bean
  public DataManipulator<NewsImage> newsImageDataManipulator(NewsImageRepository newsImageRepository) {
    return new DataManipulator<>(newsImageRepository);
  }

  @Bean
  public DataManipulator<NewsContent> newsContentDataManipulator(NewsContentRepository newsContentRepository) {
    return new DataManipulator<>(newsContentRepository);
  }

  @Bean
  public JobFactory jobFactory(ApplicationContext applicationContext) {
    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory,
      List<Trigger> triggers, @Value("${web.scheduler.name}") String schedulerName) throws IOException {
    SchedulerFactoryBean factory = new SchedulerFactoryBean();
    factory.setSchedulerName(schedulerName);
    factory.setOverwriteExistingJobs(true);
    factory.setAutoStartup(true);
    factory.setJobFactory(jobFactory);
    factory.setQuartzProperties(quartzProperties());

    // Here we will set all the trigger beans we have defined.
    if (triggers != null && !triggers.isEmpty()) {
      factory.setTriggers(triggers.toArray(new Trigger[triggers.size()]));
    }

    return factory;
  }

  @Bean
  public Properties quartzProperties() throws IOException {
    PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
    propertiesFactoryBean.setLocation(new ClassPathResource("/backup/backup.properties"));
    propertiesFactoryBean.afterPropertiesSet();
    return propertiesFactoryBean.getObject();
  }

}
