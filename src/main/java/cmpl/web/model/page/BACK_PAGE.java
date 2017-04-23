package cmpl.web.model.page;

public enum BACK_PAGE {

  LOGIN("login.tile", ""),
  NEWS_VIEW("news.view", "back.news.title"),
  NEWS_CREATE("news.create", "back.news.title"),
  NEWS_UPDATE("news.update", "back.news.title");

  private String tile;
  private String title;

  private BACK_PAGE(String tile, String title) {
    this.tile = tile;
    this.title = title;
  }

  public String getTile() {
    return tile;
  }

  public String getTitle() {
    return title;
  }

}
