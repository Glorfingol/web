package com.cmpl.web.builder;

import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.social.facebook.api.Reference;

public class ReferenceBuilder {

  private String name;

  public ReferenceBuilder name(String name) {
    this.name = name;
    return this;
  }

  public Reference toReference() {
    Reference reference = Mockito.mock(Reference.class);

    BDDMockito.doReturn(name).when(reference).getName();

    return reference;
  }

}
