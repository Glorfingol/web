package com.cmpl.web.backup.importer;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.core.backup.reader.CommonParser;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.news.NewsContent;

public class NewsContentCSVParser extends CommonParser<NewsContent> {

  public NewsContentCSVParser(DateTimeFormatter dateFormatter, DataManipulator<NewsContent> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected NewsContent parseEntity(CSVRecord record) {
    NewsContent newsContentParsed = new NewsContent();

    List<Field> fieldsToParse = getFieldsToParse(newsContentParsed.getClass());

    parseObject(record, newsContentParsed, fieldsToParse);

    return newsContentParsed;
  }

  @Override
  public String getParserName() {
    return "news_contents";
  }

}
