package cmpl.web.model.page;

/**
 * Enumeration pour les pages du back office du site
 * 
 * @author Louis
 *
 */
public enum BACK_PAGE {

  LOGIN("login.tile", ""),
  NEWS_VIEW("news.view", "back.news.view.title"),
  NEWS_CREATE("news.create", "back.news.create.title"),
  NEWS_UPDATE("news.update", "back.news.update.title"),
  FACEBOOK_ACCESS("facebook.access.tile", "facebook.access.title"),
  FACEBOOK_IMPORT("facebook.import.tile", "facebook.access.title");

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
