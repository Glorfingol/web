package cmpl.web.model.menu;

public enum BACK_MENU {

  BACK_NEWS("back.news.title", "back.news.href", "back.news.label"),
  BACK_FACEBOOK("facebook.access.title", "facebook.access.href", "facebook.access.label");

  private String title;
  private String href;
  private String label;

  private BACK_MENU(String title, String href, String label) {
    this.title = title;
    this.href = href;
    this.label = label;
  }

  public String getTitle() {
    return title;
  }

  public String getHref() {
    return href;
  }

  public String getLabel() {
    return label;
  }

}
