package com.cmpl.web.core.page;

/**
 * Enumeration pour les pages du back office du site
 * 
 * @author Louis
 *
 */
public enum BACK_PAGE {

  INDEX("index.tile", "back.index.title"),
  LOGIN("login.tile", ""),
  NEWS_VIEW("news.view", "back.news.title"),
  NEWS_CREATE("news.create", "back.news.title"),
  NEWS_UPDATE("news.update", "back.news.title"),
  NEWS_TEMPLATE("news.template", "back.news.title"),
  FACEBOOK_ACCESS("facebook.access.tile", "facebook.access.title"),
  FACEBOOK_IMPORT("facebook.import.tile", "facebook.access.title"),
  MENUS_VIEW("menus.view", "back.menus.title"),
  MENUS_CREATE("menus.create", "back.menus.title"),
  MENUS_UPDATE("menus.update", "back.menus.title"),
  PAGES_VIEW("pages.view", "back.pages.title"),
  PAGES_CREATE("pages.create", "back.pages.title"),
  PAGES_UPDATE("pages.update", "back.pages.title"),
  MEDIA_VIEW("medias.view", "back.medias.title"),
  MEDIA_UPLOAD("medias.upload", "back.medias.title"),
  CAROUSELS_VIEW("carousels.view", "back.carousels.title"),
  CAROUSELS_UPDATE("carousels.update", "back.carousels.title"),
  CAROUSELS_CREATE("carousels.create", "back.carousels.title"),
  STYLES_VIEW("styles.view", "back.style.title"),
  STYLES_UPDATE("styles.update", "back.style.title");

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
