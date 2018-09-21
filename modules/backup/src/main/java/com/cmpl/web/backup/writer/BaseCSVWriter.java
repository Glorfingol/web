package com.cmpl.web.backup.writer;

import com.cmpl.web.core.common.reflexion.CommonReflexion;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

public class BaseCSVWriter extends CommonReflexion {


  protected FileWriter createFileWriterForEntity(String rootPath, String fileName)
      throws IOException {
    return new FileWriter(rootPath + File.separator + fileName);
  }

  protected CSVPrinter createCSVPrinter(FileWriter fileWriter) throws IOException {
    CSVFormat csvFileFormat = CSVFormat.EXCEL.withNullString("").withQuoteMode(QuoteMode.ALL);
    return new CSVPrinter(fileWriter, csvFileFormat);
  }

}
