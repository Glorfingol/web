package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.CarouselItem;
import java.time.format.DateTimeFormatter;

public class CarouselItemCSVWriter extends CommonWriter<CarouselItem> {

  public CarouselItemCSVWriter(DateTimeFormatter dateFormatter,
      DataManipulator<CarouselItem> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "carousel_items";
  }

}
