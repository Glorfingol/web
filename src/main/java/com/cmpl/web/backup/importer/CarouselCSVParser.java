package com.cmpl.web.backup.importer;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.carousel.Carousel;
import com.cmpl.web.core.backup.reader.CommonParser;
import com.cmpl.web.core.backup.writer.DataManipulator;

public class CarouselCSVParser extends CommonParser<Carousel> {

  public CarouselCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Carousel> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Carousel parseEntity(CSVRecord record) {
    Carousel carouselParsed = new Carousel();

    List<Field> fieldsToParse = getFieldsToParse(carouselParsed.getClass());

    parseObject(record, carouselParsed, fieldsToParse);

    return carouselParsed;
  }

  @Override
  public String getParserName() {
    return "carousels";
  }

}
