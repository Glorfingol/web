package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.NewsEntry;
import java.time.format.DateTimeFormatter;

public class NewsEntryCSVWriter extends CommonWriter<NewsEntry> {

  public NewsEntryCSVWriter(DateTimeFormatter dateFormatter,
      DataManipulator<NewsEntry> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "news_entries";
  }

}
