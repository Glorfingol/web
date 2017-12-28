package com.cmpl.web.backup.importer;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.core.backup.reader.CommonParser;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.news.NewsEntry;

public class NewsEntryCSVParser extends CommonParser<NewsEntry> {

  public NewsEntryCSVParser(DateTimeFormatter dateFormatter, DataManipulator<NewsEntry> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected NewsEntry parseEntity(CSVRecord record) {
    NewsEntry newsEntryParsed = new NewsEntry();

    List<Field> fieldsToParse = getFields(newsEntryParsed.getClass());

    parseObject(record, newsEntryParsed, fieldsToParse);

    return newsEntryParsed;
  }

  @Override
  public String getParserName() {
    return "news_entries";
  }

}
