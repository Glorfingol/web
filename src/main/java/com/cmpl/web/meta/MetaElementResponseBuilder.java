package com.cmpl.web.meta;

import com.cmpl.web.core.builder.Builder;
import com.cmpl.web.core.model.Error;

public class MetaElementResponseBuilder extends Builder<MetaElementResponse> {

  private Error error;
  private MetaElementDTO metaElement;
  private OpenGraphMetaElementDTO openGraphMetaElement;

  private MetaElementResponseBuilder() {

  }

  public MetaElementResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  public MetaElementResponseBuilder metaElement(MetaElementDTO metaElement) {
    this.metaElement = metaElement;
    return this;
  }

  public MetaElementResponseBuilder openGraphMetaElement(OpenGraphMetaElementDTO openGraphMetaElement) {
    this.openGraphMetaElement = openGraphMetaElement;
    return this;
  }

  @Override
  public MetaElementResponse build() {
    MetaElementResponse response = new MetaElementResponse();
    response.setError(error);
    response.setMetaElement(metaElement);
    response.setOpenGraphMetaElement(openGraphMetaElement);
    return response;
  }

  public static MetaElementResponseBuilder create() {
    return new MetaElementResponseBuilder();
  }

}
