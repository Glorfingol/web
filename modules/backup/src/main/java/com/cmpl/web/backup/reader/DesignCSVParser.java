package com.cmpl.web.backup.reader;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.models.Design;
import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.csv.CSVRecord;

public class DesignCSVParser extends CommonParser<Design> {

  public DesignCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Design> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Design parseEntity(CSVRecord record) {
    Design design = new Design();

    List<Field> fieldsToParse = getFields(design.getClass());

    parseObject(record, design, fieldsToParse);

    return design;
  }

  @Override
  public String getParserName() {
    return "designs";
  }
}
