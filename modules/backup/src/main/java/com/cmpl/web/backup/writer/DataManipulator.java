package com.cmpl.web.backup.writer;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.BaseEntity;
import java.util.List;

public class DataManipulator<T extends BaseEntity> {

  private final BaseRepository<T> repository;

  public DataManipulator(BaseRepository<T> repository) {
    this.repository = repository;
  }

  public List<T> extractData() {
    return repository.findAll();
  }

  public void insertData(List<T> entities) {
    repository.saveAll(entities);
  }
}
