package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.Responsibility;
import java.time.format.DateTimeFormatter;

public class ResponsibilityCSVWriter extends CommonWriter<Responsibility> {

  public ResponsibilityCSVWriter(DateTimeFormatter dateFormatter,
      DataManipulator<Responsibility> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "responsibilities";
  }
}
