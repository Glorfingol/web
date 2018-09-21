package com.cmpl.web.backup.reader;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.models.Website;
import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.csv.CSVRecord;

public class WebsiteCSVParser extends CommonParser<Website> {

  public WebsiteCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Website> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Website parseEntity(CSVRecord record) {
    Website website = new Website();

    List<Field> fieldsToParse = getFields(website.getClass());

    parseObject(record, website, fieldsToParse);

    return website;
  }

  @Override
  public String getParserName() {
    return "websites";
  }
}
