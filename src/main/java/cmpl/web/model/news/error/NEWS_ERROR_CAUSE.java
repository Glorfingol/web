package cmpl.web.model.news.error;

public enum NEWS_ERROR_CAUSE {

  EMPTY_NEWS_ID("empty.id"),
  EMPTY_TITLE("empty.title"),
  EMPTY_AUTHOR("empty.author"),
  EMPTY_CONTENT("empty.content"),
  EMPTY_LEGEND("empty.legend"),
  EMPTY_SRC("empty.src"),
  EMPTY_WIDTH("empty.width"),
  EMPTY_HEIGHT("empty.height"),
  EMPTY_ALT("empty.alt");

  private String causeKey;

  private NEWS_ERROR_CAUSE(String causeKey) {
    this.causeKey = causeKey;
  }

  public String getCauseKey() {
    return causeKey;
  }

}
