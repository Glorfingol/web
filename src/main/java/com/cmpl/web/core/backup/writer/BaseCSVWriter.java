package com.cmpl.web.core.backup.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import com.cmpl.web.core.reflexion.CommonReflexion;

public class BaseCSVWriter extends CommonReflexion {

  protected static final String NEW_LINE_SEPARATOR = "\n";

  protected FileWriter createFileWriterForEntity(String rootPath, String fileName) throws IOException {
    return new FileWriter(rootPath + File.separator + fileName);
  }

  protected CSVPrinter createCSVPrinter(FileWriter fileWriter) throws IOException {
    CSVFormat csvFileFormat = CSVFormat.EXCEL.withNullString("").withQuoteMode(QuoteMode.ALL);
    return new CSVPrinter(fileWriter, csvFileFormat);
  }

}
