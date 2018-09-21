package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.Style;
import java.time.format.DateTimeFormatter;

public class StyleCSVWriter extends CommonWriter<Style> {

  public StyleCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Style> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "styles";
  }

}
