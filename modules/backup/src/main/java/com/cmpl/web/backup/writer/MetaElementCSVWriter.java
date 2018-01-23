package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.meta.MetaElement;

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
