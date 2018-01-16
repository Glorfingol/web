package com.cmpl.web.backup;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.cmpl.web.backup.exporter.CSVGeneratorImpl;
import com.cmpl.web.backup.exporter.CarouselCSVWriter;
import com.cmpl.web.backup.exporter.CarouselItemCSVWriter;
import com.cmpl.web.backup.exporter.MediaCSVWriter;
import com.cmpl.web.backup.exporter.MenuCSVWriter;
import com.cmpl.web.backup.exporter.MetaElementCSVWriter;
import com.cmpl.web.backup.exporter.NewsContentCSVWriter;
import com.cmpl.web.backup.exporter.NewsEntryCSVWriter;
import com.cmpl.web.backup.exporter.NewsImageCSVWriter;
import com.cmpl.web.backup.exporter.OpenGraphMetaElementCSVWriter;
import com.cmpl.web.backup.exporter.PageCSVWriter;
import com.cmpl.web.backup.exporter.StyleCSVWriter;
import com.cmpl.web.carousel.Carousel;
import com.cmpl.web.carousel.CarouselItem;
import com.cmpl.web.core.backup.writer.ArchiveManager;
import com.cmpl.web.core.backup.writer.ArchiveManagerImpl;
import com.cmpl.web.core.backup.writer.CSVGenerator;
import com.cmpl.web.core.backup.writer.CommonWriter;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.media.Media;
import com.cmpl.web.menu.Menu;
import com.cmpl.web.meta.MetaElement;
import com.cmpl.web.meta.OpenGraphMetaElement;
import com.cmpl.web.news.NewsContent;
import com.cmpl.web.news.NewsEntry;
import com.cmpl.web.news.NewsImage;
import com.cmpl.web.page.Page;
import com.cmpl.web.style.Style;
import com.google.api.services.drive.Drive;

@Configuration
@PropertySource("classpath:/backup/backup.properties")
public class BackupExportConfiguration {

  @Value("${backupFilePath}")
  String backupFilePath;

  @Value("${actualitesFilePath}")
  String actualitesFilePath;

  @Value("${pagesFilePath}")
  String pagesFilePath;

  @Value("${mediaFilePath}")
  String mediaFilePath;

  DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());;

  @Bean
  public ArchiveManager archiveManager(Drive driveService) {
    return new ArchiveManagerImpl(backupFilePath, mediaFilePath, pagesFilePath, actualitesFilePath, driveService);
  }

  @Bean
  @Qualifier("backupJob")
  public JobDetailFactoryBean backupJob() {
    JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
    factoryBean.setJobClass(BackupExporterJob.class);
    factoryBean.setGroup("Backup");
    factoryBean.setName("Backup");
    factoryBean.setDescription("Backup of all the data");
    factoryBean.setDurability(true);
    return factoryBean;
  }

  @Bean
  @Qualifier("backupTrigger")
  public SimpleTriggerFactoryBean backupTrigger(@Qualifier("backupJob") JobDetail backupJob) {
    SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setName("Application backup");
    factoryBean.setDescription("Periodic backup of the data of the application");
    factoryBean.setJobDetail(backupJob);
    factoryBean.setStartDelay(120 * 1000l);
    factoryBean.setRepeatInterval(24 * 60 * 60 * 1000l);
    factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
    return factoryBean;
  }

  @Bean
  public MenuCSVWriter menuCSVWriter(DataManipulator<Menu> menuDataManipulator) {
    return new MenuCSVWriter(dateFormatter, menuDataManipulator, backupFilePath);
  }

  @Bean
  public StyleCSVWriter styleCSVWriter(DataManipulator<Style> styleDataManipulator) {
    return new StyleCSVWriter(dateFormatter, styleDataManipulator, backupFilePath);
  }

  @Bean
  public PageCSVWriter pageCSVWriter(DataManipulator<Page> pageDataManipulator) {
    return new PageCSVWriter(dateFormatter, pageDataManipulator, backupFilePath);
  }

  @Bean
  public MediaCSVWriter mediaCSVWriter(DataManipulator<Media> mediaDataManipulator) {
    return new MediaCSVWriter(dateFormatter, mediaDataManipulator, backupFilePath);
  }

  @Bean
  public CarouselCSVWriter carouselCSVWriter(DataManipulator<Carousel> carouselDataManipulator) {
    return new CarouselCSVWriter(dateFormatter, carouselDataManipulator, backupFilePath);
  }

  @Bean
  public CarouselItemCSVWriter carouselItemCSVWriter(DataManipulator<CarouselItem> carouselItemDataManipulator) {
    return new CarouselItemCSVWriter(dateFormatter, carouselItemDataManipulator, backupFilePath);
  }

  @Bean
  public MetaElementCSVWriter metaElementCSVWriter(DataManipulator<MetaElement> metaElementDataManipulator) {
    return new MetaElementCSVWriter(dateFormatter, metaElementDataManipulator, backupFilePath);
  }

  @Bean
  public OpenGraphMetaElementCSVWriter openGraphMetaElementCSVWriter(
      DataManipulator<OpenGraphMetaElement> openGraphMetaElementDataManipulator) {
    return new OpenGraphMetaElementCSVWriter(dateFormatter, openGraphMetaElementDataManipulator, backupFilePath);
  }

  @Bean
  public NewsEntryCSVWriter newsEntryCSVWriter(DataManipulator<NewsEntry> newsEntryDataManipulator) {
    return new NewsEntryCSVWriter(dateFormatter, newsEntryDataManipulator, backupFilePath);
  }

  @Bean
  public NewsImageCSVWriter newsImageCSVWriter(DataManipulator<NewsImage> newsImageDataManipulator) {
    return new NewsImageCSVWriter(dateFormatter, newsImageDataManipulator, backupFilePath);
  }

  @Bean
  public NewsContentCSVWriter newsContentCSVWriter(DataManipulator<NewsContent> newsContentDataManipulator) {
    return new NewsContentCSVWriter(dateFormatter, newsContentDataManipulator, backupFilePath);
  }

  @Bean
  public CSVGenerator csvGenerator(MenuCSVWriter menuCSVWriter, StyleCSVWriter styleCSVWriter,
      PageCSVWriter pageCSVWriter, MediaCSVWriter mediaCSVWriter, CarouselCSVWriter carouselCSVWriter,
      CarouselItemCSVWriter carouselItemCSVWriter, MetaElementCSVWriter metaElementCSVWriter,
      OpenGraphMetaElementCSVWriter openGraphMetaElementCSVWriter, NewsEntryCSVWriter newsEntryCSVWriter,
      NewsImageCSVWriter newsImageCSVWriter, NewsContentCSVWriter newsContentCSVWriter) {
    List<CommonWriter<?>> writers = new ArrayList<>();
    writers.add(menuCSVWriter);
    writers.add(styleCSVWriter);
    writers.add(pageCSVWriter);
    writers.add(mediaCSVWriter);
    writers.add(carouselCSVWriter);
    writers.add(carouselItemCSVWriter);
    writers.add(metaElementCSVWriter);
    writers.add(openGraphMetaElementCSVWriter);
    writers.add(newsEntryCSVWriter);
    writers.add(newsImageCSVWriter);
    writers.add(newsContentCSVWriter);
    return new CSVGeneratorImpl(writers);
  }
}
