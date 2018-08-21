package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.Menu;
import java.time.format.DateTimeFormatter;

public class MenuCSVWriter extends CommonWriter<Menu> {

  public MenuCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Menu> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "menu";
  }

}
