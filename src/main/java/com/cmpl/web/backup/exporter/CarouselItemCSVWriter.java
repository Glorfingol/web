package com.cmpl.web.backup.exporter;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.carousel.CarouselItem;
import com.cmpl.web.core.backup.writer.CommonWriter;
import com.cmpl.web.core.backup.writer.DataManipulator;

public class CarouselItemCSVWriter extends CommonWriter<CarouselItem> {

  public CarouselItemCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<CarouselItem> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "carousel_items";
  }

}
