package com.cmpl.web.backup.exporter;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.backup.writer.CommonWriter;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.menu.Menu;

public class MenuCSVWriter extends CommonWriter<Menu> {

  public MenuCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Menu> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "menu";
  }

}
