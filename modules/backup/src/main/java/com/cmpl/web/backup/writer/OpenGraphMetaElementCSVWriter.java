package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.meta.OpenGraphMetaElement;

public class OpenGraphMetaElementCSVWriter extends CommonWriter<OpenGraphMetaElement> {

  public OpenGraphMetaElementCSVWriter(DateTimeFormatter dateFormatter,
      DataManipulator<OpenGraphMetaElement> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "open_graph_meta_elements";
  }

}
