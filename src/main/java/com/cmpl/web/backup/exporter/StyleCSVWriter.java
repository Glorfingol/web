package com.cmpl.web.backup.exporter;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.backup.writer.CommonWriter;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.style.Style;

public class StyleCSVWriter extends CommonWriter<Style> {

  public StyleCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Style> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "styles";
  }

}
