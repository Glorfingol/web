package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.NewsImage;
import java.time.format.DateTimeFormatter;

public class NewsImageCSVWriter extends CommonWriter<NewsImage> {

  public NewsImageCSVWriter(DateTimeFormatter dateFormatter,
      DataManipulator<NewsImage> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "news_images";
  }

}
