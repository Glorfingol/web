package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.Website;
import java.time.format.DateTimeFormatter;

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
