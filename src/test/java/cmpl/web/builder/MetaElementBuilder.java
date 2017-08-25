package cmpl.web.builder;

import cmpl.web.meta.MetaElementToDelete;

public class MetaElementBuilder {

  private String name;
  private String content;
  private String property;

  public MetaElementBuilder name(String name) {
    this.name = name;
    return this;
  }

  public MetaElementBuilder content(String content) {
    this.content = content;
    return this;
  }

  public MetaElementBuilder property(String property) {
    this.property = property;
    return this;
  }

  public MetaElementToDelete toMetaElement() {
    MetaElementToDelete metaElement = new MetaElementToDelete();
    metaElement.setName(name);
    metaElement.setContent(content);
    metaElement.setProperty(property);
    return metaElement;
  }

}
