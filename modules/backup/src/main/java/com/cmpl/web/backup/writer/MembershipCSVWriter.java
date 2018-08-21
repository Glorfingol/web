package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.Membership;
import java.time.format.DateTimeFormatter;

public class MembershipCSVWriter extends CommonWriter<Membership> {

  public MembershipCSVWriter(DateTimeFormatter dateFormatter,
      DataManipulator<Membership> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "memberships";
  }
}
