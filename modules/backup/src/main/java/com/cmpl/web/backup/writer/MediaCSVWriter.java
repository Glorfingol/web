package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.Media;
import java.time.format.DateTimeFormatter;

public class MediaCSVWriter extends CommonWriter<Media> {

  public MediaCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Media> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "media";
  }

}
