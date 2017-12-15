package com.cmpl.web.core.backup.reader;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.core.model.BaseEntity;

@FunctionalInterface
public interface DataCSVParser<T extends BaseEntity> {

  public T parseEntity(CSVRecord record);

}
