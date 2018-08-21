package com.cmpl.web.core.page;

public class BackPage implements BackPagePlugin {

  private String pageName;

  private String titleKey;

  private String templatePath;

  private boolean decorated;

  public String getPageName() {
    return pageName;
  }

  public void setPageName(String pageName) {
    this.pageName = pageName;
  }

  public String getTemplatePath() {
    return templatePath;
  }

  public void setTemplatePath(String templatePath) {
    this.templatePath = templatePath;
  }

  public boolean isDecorated() {
    return decorated;
  }

  public void setDecorated(boolean decorated) {
    this.decorated = decorated;
  }

  public String getTitleKey() {
    return titleKey;
  }

  public void setTitleKey(String titleKey) {
    this.titleKey = titleKey;
  }

  @Override
  public boolean supports(String delimiter) {
    return pageName.equals(delimiter);
  }
}
