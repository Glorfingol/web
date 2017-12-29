package com.cmpl.web.backup.exporter;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.carousel.Carousel;
import com.cmpl.web.core.backup.writer.CommonWriter;
import com.cmpl.web.core.backup.writer.DataManipulator;

public class CarouselCSVWriter extends CommonWriter<Carousel> {

  public CarouselCSVWriter() {

  }

  public CarouselCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Carousel> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "carousels";
  }

}
