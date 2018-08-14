package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.models.BOGroup;

public class GroupCSVWriter extends CommonWriter<BOGroup> {

  public GroupCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<BOGroup> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "groups";
  }
}
