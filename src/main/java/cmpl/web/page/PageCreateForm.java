package cmpl.web.page;

public class PageCreateForm {

  private String name = "";
  private String menuTitle = "";
  private boolean withNews = false;
  private String body = "";

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMenuTitle() {
    return menuTitle;
  }

  public void setMenuTitle(String menuTitle) {
    this.menuTitle = menuTitle;
  }

  public boolean isWithNews() {
    return withNews;
  }

  public void setWithNews(boolean withNews) {
    this.withNews = withNews;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

}
