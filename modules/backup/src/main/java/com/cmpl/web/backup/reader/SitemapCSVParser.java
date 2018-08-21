package com.cmpl.web.backup.reader;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.models.Sitemap;
import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.csv.CSVRecord;

public class SitemapCSVParser extends CommonParser<Sitemap> {

  public SitemapCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Sitemap> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Sitemap parseEntity(CSVRecord record) {
    Sitemap sitemap = new Sitemap();

    List<Field> fieldsToParse = getFields(sitemap.getClass());

    parseObject(record, sitemap, fieldsToParse);

    return sitemap;
  }

  @Override
  public String getParserName() {
    return "sitemaps";
  }
}
