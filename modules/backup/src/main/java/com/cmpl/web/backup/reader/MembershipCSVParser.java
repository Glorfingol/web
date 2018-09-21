package com.cmpl.web.backup.reader;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.models.Membership;
import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.csv.CSVRecord;

public class MembershipCSVParser extends CommonParser<Membership> {

  public MembershipCSVParser(DateTimeFormatter dateFormatter,
      DataManipulator<Membership> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Membership parseEntity(CSVRecord record) {
    Membership membership = new Membership();

    List<Field> fieldsToParse = getFields(membership.getClass());

    parseObject(record, membership, fieldsToParse);

    return membership;
  }

  @Override
  public String getParserName() {
    return "memberships";
  }
}
