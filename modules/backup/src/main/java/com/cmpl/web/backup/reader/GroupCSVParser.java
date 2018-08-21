package com.cmpl.web.backup.reader;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.models.BOGroup;
import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.csv.CSVRecord;

public class GroupCSVParser extends CommonParser<BOGroup> {

  public GroupCSVParser(DateTimeFormatter dateFormatter, DataManipulator<BOGroup> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected BOGroup parseEntity(CSVRecord record) {
    BOGroup groupParsed = new BOGroup();

    List<Field> fieldsToParse = getFields(groupParsed.getClass());

    parseObject(record, groupParsed, fieldsToParse);

    return groupParsed;
  }

  @Override
  public String getParserName() {
    return "groups";
  }
}
