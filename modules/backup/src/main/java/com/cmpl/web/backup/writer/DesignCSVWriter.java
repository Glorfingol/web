package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.models.Design;

public class DesignCSVWriter extends CommonWriter<Design> {

  public DesignCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Design> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "design";
  }
}
