package com.cmpl.web.backup.reader;

import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCSVReader implements CSVReader {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCSVReader.class);

  private final List<CommonParser<?>> parsers;

  public DefaultCSVReader(List<CommonParser<?>> parsers) {

    this.parsers = Objects.requireNonNull(parsers);
  }

  @Override
  public void extractAllDataFromCSVFiles() {
    this.parsers.stream().forEach(this::executeParser);
  }

  private void executeParser(CommonParser<?> parser) {
    LOGGER.info("Extraction des " + parser.getParserName());
    parser.parseCSVFile();

  }

}
