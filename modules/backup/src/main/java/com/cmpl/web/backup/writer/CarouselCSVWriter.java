package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.Carousel;
import java.time.format.DateTimeFormatter;

public class CarouselCSVWriter extends CommonWriter<Carousel> {

  public CarouselCSVWriter() {

  }

  public CarouselCSVWriter(DateTimeFormatter dateFormatter,
      DataManipulator<Carousel> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "carousels";
  }

}
