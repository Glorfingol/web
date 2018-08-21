package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.Role;
import java.time.format.DateTimeFormatter;

public class RoleCSVWriter extends CommonWriter<Role> {

  public RoleCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Role> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "roles";
  }
}
