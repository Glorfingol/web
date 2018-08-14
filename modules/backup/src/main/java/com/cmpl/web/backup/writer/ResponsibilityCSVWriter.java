package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.models.Responsibility;

public class ResponsibilityCSVWriter extends CommonWriter<Responsibility> {

  public ResponsibilityCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Responsibility> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "responsibilities";
  }
}
