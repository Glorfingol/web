package cmpl.web.meta;

import cmpl.web.core.model.BaseDTO;

public class MetaElementDTO extends BaseDTO {

  private String pageId;
  private String name;
  private String content;

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
