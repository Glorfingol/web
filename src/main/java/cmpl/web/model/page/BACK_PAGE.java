package cmpl.web.model.page;

public enum BACK_PAGE {

  LOGIN("login.tile"),
  NEWS_VIEW("news.view"),
  NEWS_CREATE("news.create"),
  NEWS_UPDATE("news.update");

  private String tile;

  private BACK_PAGE(String tile) {
    this.tile = tile;
  }

  public String getTile() {
    return tile;
  }

}
