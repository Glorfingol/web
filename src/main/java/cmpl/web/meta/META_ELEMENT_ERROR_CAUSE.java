package cmpl.web.meta;

public enum META_ELEMENT_ERROR_CAUSE {

  EMPTY_NAME("empty.meta.name"),
  EMPTY_CONTENT("empty.meta.content"),
  EMPTY_PAGE_ID("empty.meta.pageId"),
  EMPTY_PROPERTY("empty.meta.property");

  private String causeKey;

  private META_ELEMENT_ERROR_CAUSE(String causeKey) {
    this.causeKey = causeKey;
  }

  public String getCauseKey() {
    return causeKey;
  }

}
