package cmpl.web.builder.impl;

import cmpl.web.model.meta.MetaElement;

public class MetaElementTestBuilder {

  private String name;
  private String content;

  public MetaElementTestBuilder name(String name) {
    this.name = name;
    return this;
  }

  public MetaElementTestBuilder content(String content) {
    this.content = content;
    return this;
  }

  public MetaElement toMetaElement() {
    MetaElement metaElement = new MetaElement();
    metaElement.setName(name);
    metaElement.setContent(content);
    return metaElement;
  }

}
