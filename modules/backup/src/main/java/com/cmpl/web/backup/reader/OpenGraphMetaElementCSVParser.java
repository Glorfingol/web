package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.meta.OpenGraphMetaElement;

public class OpenGraphMetaElementCSVParser extends CommonParser<OpenGraphMetaElement> {

  public OpenGraphMetaElementCSVParser(DateTimeFormatter dateFormatter,
      DataManipulator<OpenGraphMetaElement> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected OpenGraphMetaElement parseEntity(CSVRecord record) {
    OpenGraphMetaElement openGrapMetaElementParsed = new OpenGraphMetaElement();

    List<Field> fieldsToParse = getFields(openGrapMetaElementParsed.getClass());

    parseObject(record, openGrapMetaElementParsed, fieldsToParse);

    return openGrapMetaElementParsed;
  }

  @Override
  public String getParserName() {
    return "open_graph_meta_elements";
  }

}
