package com.cmpl.web.backup.importer;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.core.backup.reader.CommonParser;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.news.NewsImage;

public class NewsImageCSVParser extends CommonParser<NewsImage> {

  public NewsImageCSVParser(DateTimeFormatter dateFormatter, DataManipulator<NewsImage> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected NewsImage parseEntity(CSVRecord record) {
    NewsImage newsImageParsed = new NewsImage();

    List<Field> fieldsToParse = getFieldsToParse(newsImageParsed.getClass());

    parseObject(record, newsImageParsed, fieldsToParse);

    return newsImageParsed;
  }

  @Override
  public String getParserName() {
    return "news_images";
  }

}
