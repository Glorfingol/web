package cmpl.web.model.page;

import cmpl.web.model.menu.BACK_MENU;

/**
 * Enumeration pour les pages du back office du site
 * 
 * @author Louis
 *
 */
public enum BACK_PAGE {

  LOGIN("login.tile", ""),
  NEWS_VIEW("news.view", BACK_MENU.BACK_NEWS.getTitle()),
  NEWS_CREATE("news.create", BACK_MENU.BACK_NEWS.getTitle()),
  NEWS_UPDATE("news.update", BACK_MENU.BACK_NEWS.getTitle()),
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
