package cmpl.web.page;

public class PageCreateForm {

  private String name = "";
  private String menuTitle = "";
  private boolean withNews = false;
  private String body = "";

  private String nameLabel = "";
  private String menuTitleLabel = "";
  private String bodyLabel = "";
  private String withNewsLabel = "";
  private String nameHelp = "";
  private String menuTitleHelp = "";
  private String withNewsHelp = "";
  private String bodyHelp = "";

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

  public String getNameHelp() {
    return nameHelp;
  }

  public void setNameHelp(String nameHelp) {
    this.nameHelp = nameHelp;
  }

  public String getMenuTitleHelp() {
    return menuTitleHelp;
  }

  public void setMenuTitleHelp(String menuTitleHelp) {
    this.menuTitleHelp = menuTitleHelp;
  }

  public String getWithNewsHelp() {
    return withNewsHelp;
  }

  public void setWithNewsHelp(String withNewsHelp) {
    this.withNewsHelp = withNewsHelp;
  }

  public String getNameLabel() {
    return nameLabel;
  }

  public void setNameLabel(String nameLabel) {
    this.nameLabel = nameLabel;
  }

  public String getMenuTitleLabel() {
    return menuTitleLabel;
  }

  public void setMenuTitleLabel(String menuTitleLabel) {
    this.menuTitleLabel = menuTitleLabel;
  }

  public String getWithNewsLabel() {
    return withNewsLabel;
  }

  public void setWithNewsLabel(String withNewsLabel) {
    this.withNewsLabel = withNewsLabel;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getBodyLabel() {
    return bodyLabel;
  }

  public void setBodyLabel(String bodyLabel) {
    this.bodyLabel = bodyLabel;
  }

  public String getBodyHelp() {
    return bodyHelp;
  }

  public void setBodyHelp(String bodyHelp) {
    this.bodyHelp = bodyHelp;
  }

}
