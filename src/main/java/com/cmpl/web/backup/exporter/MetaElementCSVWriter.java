package com.cmpl.web.backup.exporter;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.backup.writer.CommonWriter;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.meta.MetaElement;

public class MetaElementCSVWriter extends CommonWriter<MetaElement> {

  public MetaElementCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<MetaElement> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "meta_elements";
  }

}
