package com.cmpl.web.backup.importer;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.core.backup.reader.CommonParser;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.meta.MetaElement;

public class MetaElementCSVParser extends CommonParser<MetaElement> {

  public MetaElementCSVParser(DateTimeFormatter dateFormatter, DataManipulator<MetaElement> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected MetaElement parseEntity(CSVRecord record) {
    MetaElement metaElementParsed = new MetaElement();

    List<Field> fieldsToParse = getFieldsToParse(metaElementParsed.getClass());

    parseObject(record, metaElementParsed, fieldsToParse);

    return metaElementParsed;
  }

  @Override
  public String getParserName() {
    return "meta_elements";
  }

}
