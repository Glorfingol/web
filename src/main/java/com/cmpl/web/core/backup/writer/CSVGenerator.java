package com.cmpl.web.core.backup.writer;

@FunctionalInterface
public interface CSVGenerator {

  void extractAllDataToCSVFiles();
}
