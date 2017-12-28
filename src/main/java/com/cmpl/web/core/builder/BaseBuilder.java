package com.cmpl.web.core.builder;

import java.time.LocalDate;

public abstract class BaseBuilder<T> extends Builder<T> {

  protected Long id;
  protected LocalDate creationDate;
  protected LocalDate modificationDate;

  public BaseBuilder<T> id(Long id) {
    this.id = id;
    return this;
  }

  public BaseBuilder<T> creationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public BaseBuilder<T> modificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

}
