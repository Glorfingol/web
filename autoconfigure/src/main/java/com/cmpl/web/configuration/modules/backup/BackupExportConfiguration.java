package com.cmpl.web.configuration.modules.backup;

import com.cmpl.web.backup.BackupExporterJob;
import com.cmpl.web.backup.DefaultCSVGenerator;
import com.cmpl.web.backup.writer.ArchiveManager;
import com.cmpl.web.backup.writer.CSVGenerator;
import com.cmpl.web.backup.writer.CarouselCSVWriter;
import com.cmpl.web.backup.writer.CarouselItemCSVWriter;
import com.cmpl.web.backup.writer.CommonWriter;
import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.backup.writer.DefaultArchiveManager;
import com.cmpl.web.backup.writer.DesignCSVWriter;
import com.cmpl.web.backup.writer.GroupCSVWriter;
import com.cmpl.web.backup.writer.MediaCSVWriter;
import com.cmpl.web.backup.writer.MembershipCSVWriter;
import com.cmpl.web.backup.writer.NewsContentCSVWriter;
import com.cmpl.web.backup.writer.NewsEntryCSVWriter;
import com.cmpl.web.backup.writer.NewsImageCSVWriter;
import com.cmpl.web.backup.writer.PageCSVWriter;
import com.cmpl.web.backup.writer.PrivilegeCSVWriter;
import com.cmpl.web.backup.writer.ResponsibilityCSVWriter;
import com.cmpl.web.backup.writer.RoleCSVWriter;
import com.cmpl.web.backup.writer.SitemapCSVWriter;
import com.cmpl.web.backup.writer.StyleCSVWriter;
import com.cmpl.web.backup.writer.UserCSVWriter;
import com.cmpl.web.backup.writer.WebsiteCSVWriter;
import com.cmpl.web.backup.writer.WidgetCSVWriter;
import com.cmpl.web.backup.writer.WidgetPageCSVWriter;
import com.cmpl.web.core.models.BOGroup;
import com.cmpl.web.core.models.Carousel;
import com.cmpl.web.core.models.CarouselItem;
import com.cmpl.web.core.models.Design;
import com.cmpl.web.core.models.Media;
import com.cmpl.web.core.models.Membership;
import com.cmpl.web.core.models.NewsContent;
import com.cmpl.web.core.models.NewsEntry;
import com.cmpl.web.core.models.NewsImage;
import com.cmpl.web.core.models.Page;
import com.cmpl.web.core.models.Privilege;
import com.cmpl.web.core.models.Responsibility;
import com.cmpl.web.core.models.Role;
import com.cmpl.web.core.models.Sitemap;
import com.cmpl.web.core.models.Style;
import com.cmpl.web.core.models.User;
import com.cmpl.web.core.models.Website;
import com.cmpl.web.core.models.Widget;
import com.cmpl.web.core.models.WidgetPage;
import com.cmpl.web.google.DriveAdapter;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

  DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
      .withZone(ZoneId.systemDefault());

  ;

  @Bean
  public ArchiveManager archiveManager(DriveAdapter driveAdapter) {
    return new DefaultArchiveManager(backupFilePath, mediaFilePath, pagesFilePath,
        actualitesFilePath,
        driveAdapter);
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
  public CarouselItemCSVWriter carouselItemCSVWriter(
      DataManipulator<CarouselItem> carouselItemDataManipulator) {
    return new CarouselItemCSVWriter(dateFormatter, carouselItemDataManipulator, backupFilePath);
  }

  @Bean
  public NewsEntryCSVWriter newsEntryCSVWriter(
      DataManipulator<NewsEntry> newsEntryDataManipulator) {
    return new NewsEntryCSVWriter(dateFormatter, newsEntryDataManipulator, backupFilePath);
  }

  @Bean
  public NewsImageCSVWriter newsImageCSVWriter(
      DataManipulator<NewsImage> newsImageDataManipulator) {
    return new NewsImageCSVWriter(dateFormatter, newsImageDataManipulator, backupFilePath);
  }

  @Bean
  public NewsContentCSVWriter newsContentCSVWriter(
      DataManipulator<NewsContent> newsContentDataManipulator) {
    return new NewsContentCSVWriter(dateFormatter, newsContentDataManipulator, backupFilePath);
  }

  @Bean
  public WidgetCSVWriter widgetCSVWriter(DataManipulator<Widget> widgetDataManipulator) {
    return new WidgetCSVWriter(dateFormatter, widgetDataManipulator, backupFilePath);
  }

  @Bean
  public WidgetPageCSVWriter widgetPageCSVWriter(
      DataManipulator<WidgetPage> widgetPageDataManipulator) {
    return new WidgetPageCSVWriter(dateFormatter, widgetPageDataManipulator, backupFilePath);
  }

  @Bean
  public UserCSVWriter userCSVWriter(DataManipulator<User> userDataManipulator) {
    return new UserCSVWriter(dateFormatter, userDataManipulator, backupFilePath);
  }

  @Bean
  public RoleCSVWriter roleCSVWriter(DataManipulator<Role> roleDataManipulator) {
    return new RoleCSVWriter(dateFormatter, roleDataManipulator, backupFilePath);
  }

  @Bean
  public PrivilegeCSVWriter privilegeCSVWriter(
      DataManipulator<Privilege> privilegeDataManipulator) {
    return new PrivilegeCSVWriter(dateFormatter, privilegeDataManipulator, backupFilePath);
  }

  @Bean
  public ResponsibilityCSVWriter associationUserRoleCSVWriter(
      DataManipulator<Responsibility> associationUserRoleDataManipulator) {
    return new ResponsibilityCSVWriter(dateFormatter, associationUserRoleDataManipulator,
        backupFilePath);
  }

  @Bean
  public WebsiteCSVWriter websiteCSVWriter(DataManipulator<Website> websiteDataManipulator) {
    return new WebsiteCSVWriter(dateFormatter, websiteDataManipulator, backupFilePath);
  }

  @Bean
  public GroupCSVWriter groupCSVWriter(DataManipulator<BOGroup> groupDataManipulator) {
    return new GroupCSVWriter(dateFormatter, groupDataManipulator, backupFilePath);
  }

  @Bean
  public MembershipCSVWriter membershipCSVWriter(
      DataManipulator<Membership> membershipDataManipulator) {
    return new MembershipCSVWriter(dateFormatter, membershipDataManipulator, backupFilePath);
  }

  @Bean
  public DesignCSVWriter designCSVWriter(DataManipulator<Design> designDataManipulator) {
    return new DesignCSVWriter(dateFormatter, designDataManipulator, backupFilePath);
  }

  @Bean
  public SitemapCSVWriter sitemapCSVWriter(DataManipulator<Sitemap> sitemapDataManipulator) {
    return new SitemapCSVWriter(dateFormatter, sitemapDataManipulator, backupFilePath);
  }

  @Bean
  public CSVGenerator csvGenerator(UserCSVWriter userCSVWriter, RoleCSVWriter roleCSVWriter,
      PrivilegeCSVWriter privilegeCSVWriter, ResponsibilityCSVWriter associationUserRoleCSVWriter,
      StyleCSVWriter styleCSVWriter, PageCSVWriter pageCSVWriter,
      MediaCSVWriter mediaCSVWriter, CarouselCSVWriter carouselCSVWriter,
      CarouselItemCSVWriter carouselItemCSVWriter,
      NewsEntryCSVWriter newsEntryCSVWriter, NewsImageCSVWriter newsImageCSVWriter,
      NewsContentCSVWriter newsContentCSVWriter, WidgetCSVWriter widgetCSVWriter,
      WidgetPageCSVWriter widgetPageCSVWriter, WebsiteCSVWriter websiteCSVWriter,
      GroupCSVWriter groupCSVWriter,
      MembershipCSVWriter membershipCSVWriter, DesignCSVWriter designCSVWriter,
      SitemapCSVWriter sitemapCSVWriter) {

    List<CommonWriter<?>> writers = new ArrayList<>();
    writers.addAll(Arrays
        .asList(userCSVWriter, roleCSVWriter, privilegeCSVWriter, associationUserRoleCSVWriter,
            styleCSVWriter, pageCSVWriter, mediaCSVWriter, carouselCSVWriter,
            carouselItemCSVWriter,
            newsEntryCSVWriter, newsContentCSVWriter, newsImageCSVWriter, widgetCSVWriter,
            widgetPageCSVWriter,
            websiteCSVWriter, groupCSVWriter, membershipCSVWriter, designCSVWriter,
            sitemapCSVWriter));

    return new DefaultCSVGenerator(writers);
  }

  @Bean
  @Qualifier("backupExportJob")
  public JobDetailFactoryBean backupExportJob() {
    JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
    factoryBean.setJobClass(BackupExporterJob.class);
    factoryBean.setGroup("backupExportJob");
    factoryBean.setName("backupExportJob");
    factoryBean.setDescription("Backup of all the data");
    factoryBean.setDurability(true);
    return factoryBean;
  }

  @Bean
  @Qualifier("backupExportTrigger")
  public SimpleTriggerFactoryBean backupExportTrigger(JobDetail backupExportJob) {
    SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setName("Application backup export");
    factoryBean.setDescription("Periodic backup of the data of the application");
    factoryBean.setJobDetail(backupExportJob);
    factoryBean.setStartDelay(120 * 1000l);
    factoryBean.setRepeatInterval(24 * 60 * 60 * 1000l);
    factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    factoryBean.setMisfireInstruction(
        SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
    return factoryBean;
  }

}
