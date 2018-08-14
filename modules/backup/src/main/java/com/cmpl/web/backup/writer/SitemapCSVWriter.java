package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.models.Sitemap;

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
