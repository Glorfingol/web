package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.User;
import java.time.format.DateTimeFormatter;

public class UserCSVWriter extends CommonWriter<User> {

  public UserCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<User> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "users";
  }
}
