package com.cmpl.web.core.page;

import com.cmpl.web.core.common.builder.Builder;

public class BackPageBuilder extends Builder<BackPage> {

  private String pageName;

  private String titleKey;

  private String templatePath;

  private boolean decorated;

  private BackPageBuilder() {

  }

  public BackPageBuilder pageName(String pageName) {
    this.pageName = pageName;
    return this;
  }

  public BackPageBuilder titleKey(String titleKey) {
    this.titleKey = titleKey;
    return this;
  }

  public BackPageBuilder templatePath(String templatePath) {
    this.templatePath = templatePath;
    return this;
  }

  public BackPageBuilder decorated(boolean decorated) {
    this.decorated = decorated;
    return this;
  }

  @Override
  public BackPage build() {
    BackPage backPage = new BackPage();
    backPage.setDecorated(decorated);
    backPage.setPageName(pageName);
    backPage.setTemplatePath(templatePath);
    backPage.setTitleKey(titleKey);
    return backPage;
  }

  public static BackPageBuilder create() {
    return new BackPageBuilder();
  }
}
