package com.cmpl.web.meta;

import com.cmpl.web.core.builder.BaseBuilder;

public class OpenGraphMetaElementBuilder extends BaseBuilder<OpenGraphMetaElement> {

  private String pageId;
  private String property;
  private String content;

  public OpenGraphMetaElementBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  public OpenGraphMetaElementBuilder property(String property) {
    this.property = property;
    return this;
  }

  public OpenGraphMetaElementBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public OpenGraphMetaElement build() {
    OpenGraphMetaElement metaElement = new OpenGraphMetaElement();
    metaElement.setContent(content);
    metaElement.setCreationDate(creationDate);
    metaElement.setId(id);
    metaElement.setModificationDate(modificationDate);
    metaElement.setProperty(property);
    metaElement.setPageId(pageId);
    return metaElement;
  }

}
