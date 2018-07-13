package com.cmpl.web.backup.writer;

import com.cmpl.web.core.models.Responsibility;
import java.time.format.DateTimeFormatter;

public class AssociationUserRoleCSVWriter extends CommonWriter<Responsibility> {

  public AssociationUserRoleCSVWriter(DateTimeFormatter dateFormatter,
      DataManipulator<Responsibility> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "associations_user_role";
  }
}
