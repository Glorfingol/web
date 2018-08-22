package com.cmpl.web.backup;

import com.cmpl.web.backup.writer.CSVGenerator;
import com.cmpl.web.backup.writer.CommonWriter;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCSVGenerator implements CSVGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCSVGenerator.class);

  private final List<CommonWriter<?>> writers;

  public DefaultCSVGenerator(List<CommonWriter<?>> writers) {

    this.writers = Objects.requireNonNull(writers);
  }

  @Override
  public void extractAllDataToCSVFiles() {
    this.writers.stream().forEach(this::executeWriter);
  }

  private void executeWriter(CommonWriter<?> writer) {
    LOGGER.info("Extraction des " + writer.getWriterName());
    writer.writeCSVFile();

  }

}
