package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.BOGroup;
import java.time.format.DateTimeFormatter;

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
