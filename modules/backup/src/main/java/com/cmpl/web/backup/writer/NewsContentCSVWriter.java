package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.NewsContent;
import java.time.format.DateTimeFormatter;

public class NewsContentCSVWriter extends CommonWriter<NewsContent> {

  public NewsContentCSVWriter(DateTimeFormatter dateFormatter,
      DataManipulator<NewsContent> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "news_contents";
  }

}
