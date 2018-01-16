package com.cmpl.web.meta;

import com.cmpl.web.core.builder.BaseBuilder;

public class MetaElementBuilder extends BaseBuilder<MetaElement> {

  private String pageId;
  private String name;
  private String content;

  private MetaElementBuilder() {

  }

  public MetaElementBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  public MetaElementBuilder name(String name) {
    this.name = name;
    return this;
  }

  public MetaElementBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public MetaElement build() {
    MetaElement metaElement = new MetaElement();
    metaElement.setContent(content);
    metaElement.setCreationDate(creationDate);
    metaElement.setId(id);
    metaElement.setModificationDate(modificationDate);
    metaElement.setName(name);
    metaElement.setPageId(pageId);
    return metaElement;
  }

  public static MetaElementBuilder create() {
    return new MetaElementBuilder();
  }

}
