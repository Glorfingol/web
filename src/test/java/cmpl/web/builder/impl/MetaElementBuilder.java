package cmpl.web.builder.impl;

import cmpl.web.model.meta.MetaElement;

public class MetaElementBuilder {

  private String name;
  private String content;

  public MetaElementBuilder name(String name) {
    this.name = name;
    return this;
  }

  public MetaElementBuilder content(String content) {
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
