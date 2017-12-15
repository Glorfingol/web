package com.cmpl.web.backup.importer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmpl.web.backup.exporter.CSVGeneratorImpl;
import com.cmpl.web.core.backup.reader.CSVReader;
import com.cmpl.web.core.backup.reader.CommonParser;

public class CSVReaderImpl implements CSVReader {

  private static final Logger LOGGER = LoggerFactory.getLogger(CSVGeneratorImpl.class);

  private final List<CommonParser<?>> parsers;

  public CSVReaderImpl(List<CommonParser<?>> parsers) {
    this.parsers = parsers;
  }

  @Override
  public void extractAllDataFromCSVFiles() {
    this.parsers.stream().forEach(parser -> executeParser(parser));
  }

  private void executeParser(CommonParser<?> parser) {
    LOGGER.info("Extraction des " + parser.getParserName());
    parser.parseCSVFile();

  }

}
