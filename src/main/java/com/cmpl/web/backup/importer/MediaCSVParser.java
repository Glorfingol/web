package com.cmpl.web.backup.importer;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.core.backup.reader.CommonParser;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.media.Media;

public class MediaCSVParser extends CommonParser<Media> {

  public MediaCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Media> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Media parseEntity(CSVRecord record) {
    Media mediaParsed = new Media();

    List<Field> fieldsToParse = getFieldsToParse(mediaParsed.getClass());

    parseObject(record, mediaParsed, fieldsToParse);

    return mediaParsed;
  }

  @Override
  public String getParserName() {
    return "media";
  }

}
