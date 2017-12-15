package com.cmpl.web.backup;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.cmpl.web.backup.importer.BackupImporter;
import com.cmpl.web.backup.importer.CSVReaderImpl;
import com.cmpl.web.backup.importer.CarouselCSVParser;
import com.cmpl.web.backup.importer.CarouselItemCSVParser;
import com.cmpl.web.backup.importer.MediaCSVParser;
import com.cmpl.web.backup.importer.MenuCSVParser;
import com.cmpl.web.backup.importer.MetaElementCSVParser;
import com.cmpl.web.backup.importer.NewsContentCSVParser;
import com.cmpl.web.backup.importer.NewsEntryCSVParser;
import com.cmpl.web.backup.importer.NewsImageCSVParser;
import com.cmpl.web.backup.importer.OpenGraphMetaElementCSVParser;
import com.cmpl.web.backup.importer.PageCSVParser;
import com.cmpl.web.backup.importer.StyleCSVParser;
import com.cmpl.web.carousel.Carousel;
import com.cmpl.web.carousel.CarouselItem;
import com.cmpl.web.core.backup.reader.CSVReader;
import com.cmpl.web.core.backup.reader.CommonParser;
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

@Configuration
@PropertySource("classpath:/backup/backup.properties")
public class BackupImportConfiguration {

  @Value("${backupFilePath}")
  String backupFilePath;

  DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Bean
  @Qualifier("backupImportJob")
  public JobDetailFactoryBean backupImportJob() {
    JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
    factoryBean.setJobClass(BackupImporterJob.class);
    factoryBean.setGroup("Backup_import");
    factoryBean.setName("Backup_import");
    factoryBean.setDescription("Backup import of all the data");
    factoryBean.setDurability(true);
    return factoryBean;
  }

  @Bean
  @Qualifier("backupImportTrigger")
  public SimpleTriggerFactoryBean backupImportTrigger(@Qualifier("backupImportJob") JobDetail backupImportJob) {
    SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setName("Application backup import");
    factoryBean.setDescription("Startup backup import of the data of the application");
    factoryBean.setJobDetail(backupImportJob);
    factoryBean.setStartDelay(10 * 1000l);
    factoryBean.setRepeatCount(0);
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
    return factoryBean;
  }

  @Bean
  public BackupImporter backupImporter(CSVReader csvReader) {
    return new BackupImporter(csvReader, backupFilePath);
  }

  @Bean
  public MenuCSVParser menuCSVParser(DataManipulator<Menu> menuDataManipulator) {
    return new MenuCSVParser(dateFormatter, menuDataManipulator, backupFilePath);
  }

  @Bean
  public StyleCSVParser styleCSVParser(DataManipulator<Style> styleDataManipulator) {
    return new StyleCSVParser(dateFormatter, styleDataManipulator, backupFilePath);
  }

  @Bean
  public PageCSVParser pageCSVParser(DataManipulator<Page> pageDataManipulator) {
    return new PageCSVParser(dateFormatter, pageDataManipulator, backupFilePath);
  }

  @Bean
  public MediaCSVParser mediaCSVParser(DataManipulator<Media> mediaDataManipulator) {
    return new MediaCSVParser(dateFormatter, mediaDataManipulator, backupFilePath);
  }

  @Bean
  public CarouselCSVParser carouselCSVParser(DataManipulator<Carousel> carouselDataManipulator) {
    return new CarouselCSVParser(dateFormatter, carouselDataManipulator, backupFilePath);
  }

  @Bean
  public CarouselItemCSVParser carouselItemCSVParser(DataManipulator<CarouselItem> carouselItemDataManipulator) {
    return new CarouselItemCSVParser(dateFormatter, carouselItemDataManipulator, backupFilePath);
  }

  @Bean
  public MetaElementCSVParser metaElementCSVParser(DataManipulator<MetaElement> metaElementDataManipulator) {
    return new MetaElementCSVParser(dateFormatter, metaElementDataManipulator, backupFilePath);
  }

  @Bean
  public OpenGraphMetaElementCSVParser openGraphMetaElementCSVParser(
      DataManipulator<OpenGraphMetaElement> openGraphMetaElementDataManipulator) {
    return new OpenGraphMetaElementCSVParser(dateFormatter, openGraphMetaElementDataManipulator, backupFilePath);
  }

  @Bean
  public NewsEntryCSVParser newsEntryCSVParser(DataManipulator<NewsEntry> newsEntryDataManipulator) {
    return new NewsEntryCSVParser(dateFormatter, newsEntryDataManipulator, backupFilePath);
  }

  @Bean
  public NewsImageCSVParser newsImageCSVParser(DataManipulator<NewsImage> newsImageDataManipulator) {
    return new NewsImageCSVParser(dateFormatter, newsImageDataManipulator, backupFilePath);
  }

  @Bean
  public NewsContentCSVParser newsContentCSVParser(DataManipulator<NewsContent> newsContentDataManipulator) {
    return new NewsContentCSVParser(dateFormatter, newsContentDataManipulator, backupFilePath);
  }

  @Bean
  public CSVReader csvReader(MenuCSVParser menuCSVParser, StyleCSVParser styleCSVParser, PageCSVParser pageCSVParser,
      MediaCSVParser mediaCSVParser, CarouselCSVParser carouselCSVParser, CarouselItemCSVParser carouselItemCSVParser,
      MetaElementCSVParser metaElementCSVParser, OpenGraphMetaElementCSVParser openGraphMetaElementCSVParser,
      NewsEntryCSVParser newsEntryCSVParser, NewsImageCSVParser newsImageCSVParser,
      NewsContentCSVParser newsContentCSVParser) {
    List<CommonParser<?>> parsers = new ArrayList<>();
    parsers.add(menuCSVParser);
    parsers.add(styleCSVParser);
    parsers.add(pageCSVParser);
    parsers.add(mediaCSVParser);
    parsers.add(carouselCSVParser);
    parsers.add(carouselItemCSVParser);
    parsers.add(metaElementCSVParser);
    parsers.add(openGraphMetaElementCSVParser);
    parsers.add(newsEntryCSVParser);
    parsers.add(newsImageCSVParser);
    parsers.add(newsContentCSVParser);
    return new CSVReaderImpl(parsers);
  }

}
