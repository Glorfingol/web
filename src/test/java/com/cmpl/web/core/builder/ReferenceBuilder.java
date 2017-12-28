package com.cmpl.web.core.builder;

import org.mockito.BDDMockito;
import org.springframework.social.facebook.api.Reference;

public class ReferenceBuilder extends Builder<Reference> {

  private String name;

  public ReferenceBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Reference build() {
    Reference reference = BDDMockito.mock(Reference.class);

    BDDMockito.doReturn(name).when(reference).getName();

    return reference;
  }

}
