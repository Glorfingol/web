package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.Privilege;
import java.time.format.DateTimeFormatter;

public class PrivilegeCSVWriter extends CommonWriter<Privilege> {

  public PrivilegeCSVWriter(DateTimeFormatter dateFormatter,
      DataManipulator<Privilege> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "privileges";
  }
}
