package cmpl.web.model.news;

public enum METHOD {

  SETTER("set"),
  GETTER("get"),
  BOOLEAN_GETTER("is");

  private String prefix;

  private METHOD(String prefix) {
    this.prefix = prefix;
  }

  public String getPrefix() {
    return prefix;
  }

}
