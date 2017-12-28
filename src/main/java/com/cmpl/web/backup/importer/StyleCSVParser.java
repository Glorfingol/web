package com.cmpl.web.backup.importer;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.core.backup.reader.CommonParser;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.style.Style;

public class StyleCSVParser extends CommonParser<Style> {

  public StyleCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Style> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Style parseEntity(CSVRecord record) {
    Style styleParsed = new Style();

    List<Field> fieldsToParse = getFields(styleParsed.getClass());

    parseObject(record, styleParsed, fieldsToParse);

    return styleParsed;
  }

  @Override
  public String getParserName() {
    return "styles";
  }

}
