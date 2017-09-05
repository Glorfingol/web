package cmpl.web.page;

public enum PAGE_ERROR_CAUSE {

  EMPTY_NAME("empty.name"),
  EMPTY_MENU_TITLE("empty.menuTitle"),
  USED_NAME("used.name"),
  USED_MENU_TITLE("used.menuTitle");

  private String causeKey;

  private PAGE_ERROR_CAUSE(String causeKey) {
    this.causeKey = causeKey;
  }

  public String getCauseKey() {
    return causeKey;
  }
}
