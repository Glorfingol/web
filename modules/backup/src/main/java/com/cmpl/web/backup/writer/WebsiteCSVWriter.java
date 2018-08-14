package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.models.Website;

public class WebsiteCSVWriter extends CommonWriter<Website> {

  public WebsiteCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Website> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "websites";
  }
}
