package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.Page;
import java.time.format.DateTimeFormatter;

public class PageCSVWriter extends CommonWriter<Page> {

  public PageCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Page> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "pages";
  }

}
