package com.cmpl.web.backup.reader;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.models.Responsibility;
import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.csv.CSVRecord;

public class ResponsibilityCSVParser extends CommonParser<Responsibility> {

  public ResponsibilityCSVParser(DateTimeFormatter dateFormatter,
      DataManipulator<Responsibility> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Responsibility parseEntity(CSVRecord record) {
    Responsibility associationParsed = new Responsibility();

    List<Field> fieldsToParse = getFields(associationParsed.getClass());

    parseObject(record, associationParsed, fieldsToParse);

    return associationParsed;
  }

  @Override
  public String getParserName() {
    return "responsibilities";
  }
}
