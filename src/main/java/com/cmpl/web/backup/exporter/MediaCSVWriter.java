package com.cmpl.web.backup.exporter;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.backup.writer.CommonWriter;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.media.Media;

public class MediaCSVWriter extends CommonWriter<Media> {

  public MediaCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Media> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "media";
  }

}
