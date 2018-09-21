package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.Sitemap;
import java.time.format.DateTimeFormatter;

public class SitemapCSVWriter extends CommonWriter<Sitemap> {

  public SitemapCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Sitemap> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "sitemaps";
  }
}
