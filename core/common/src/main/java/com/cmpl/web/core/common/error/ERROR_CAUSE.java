package com.cmpl.web.core.common.error;

public enum ERROR_CAUSE {

  EMPTY_META_ID("empty.meta.id"),
  EMPTY_META_NAME("empty.meta.name"),
  EMPTY_META_CONTENT("empty.meta.content"),
  EMPTY_META_PAGE_ID("empty.meta.pageId"),
  EMPTY_META_PROPERTY("empty.meta.property"),

  EMPTY_NEWS_ID("empty.id"),
  EMPTY_NEWS_TITLE("empty.title"),
  EMPTY_NEWS_AUTHOR("empty.author"),
  EMPTY_NEWS_CONTENT("empty.content"),
  EMPTY_NEWS_LEGEND("empty.legend"),
  EMPTY_NEWS_SRC("empty.src"),
  INVALID_NEWS_FORMAT("invalid.format"),
  EMPTY_NEWS_ALT("empty.alt"),

  EMPTY_PAGE_NAME("empty.name"),
  EMPTY_PAGE_MENU_TITLE("empty.menuTitle"),
  USED_NAME("used.name"),
  USED_MENU_TITLE("used.menuTitle"),

  EMPTY_MENU_TITLE("empty.menu.title"),
  EMPTY_PAGE("empty.menu.page"),
  BAD_ORDER("bad.menu.order"),

  EMPTY_CAROUSEL_NAME("empty.carousel.name"),
  EMPTY_CAROUSEL_ID("empty.carousel.id"),
  EMPTY_MEDIA_ID("empty.media.id"),
  EMPTY_CAROUSEL_PAGE("empty.carousel.page"),
  EMPTY_CAROUSEL_ITEM_ID("empty.carousel.item.id");

  private String causeKey;

  private ERROR_CAUSE(String causeKey) {
    this.causeKey = causeKey;
  }

  public String getCauseKey() {
    return causeKey;
  }

}
