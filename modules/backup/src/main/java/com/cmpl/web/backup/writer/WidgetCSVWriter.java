package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.Widget;
import java.time.format.DateTimeFormatter;

public class WidgetCSVWriter extends CommonWriter<Widget> {

  public WidgetCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Widget> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "widgets";
  }
}
