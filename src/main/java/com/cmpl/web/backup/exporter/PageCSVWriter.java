package com.cmpl.web.backup.exporter;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.backup.writer.CommonWriter;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.page.Page;

public class PageCSVWriter extends CommonWriter<Page> {

  public PageCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Page> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "pages";
  }

}
