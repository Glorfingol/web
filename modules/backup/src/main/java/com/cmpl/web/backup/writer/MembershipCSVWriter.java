package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.models.Membership;

public class MembershipCSVWriter extends CommonWriter<Membership> {

  public MembershipCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Membership> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "memberships";
  }
}
