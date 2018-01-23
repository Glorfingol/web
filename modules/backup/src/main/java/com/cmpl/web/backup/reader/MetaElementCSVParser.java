package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.meta.MetaElement;

public class MetaElementCSVParser extends CommonParser<MetaElement> {

  public MetaElementCSVParser(DateTimeFormatter dateFormatter, DataManipulator<MetaElement> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected MetaElement parseEntity(CSVRecord record) {
    MetaElement metaElementParsed = new MetaElement();

    List<Field> fieldsToParse = getFields(metaElementParsed.getClass());

    parseObject(record, metaElementParsed, fieldsToParse);

    return metaElementParsed;
  }

  @Override
  public String getParserName() {
    return "meta_elements";
  }

}
