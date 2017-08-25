package cmpl.web.menu;

import cmpl.web.page.PAGES;

/**
 * Enumeration pour les elements du menu des pages du front office du site
 * 
 * @author Louis
 *
 */
public enum MENUS {

  INDEX(PAGES.INDEX.getTitle(), "index.href", "index.label"),
  CENTER(PAGES.CENTER.getTitle(), "center.href", "center.label"),
  GYNECOLOGIST(PAGES.GYNECOLOGIST.getTitle(), "gynecologist.href", "gynecologist.label"),
  MEDICAL_CARE(PAGES.MEDICAL_CARE.getTitle(), "medical.care.href", "medical.care.label"),
  TECHNICS(PAGES.TECHNICS.getTitle(), "technics.href", "technics.label"),
  PRICES(PAGES.PRICES.getTitle(), "prices.href", "prices.label"),
  NEWS(PAGES.NEWS.getTitle(), "news.href", "news.label"),
  CONTACT(PAGES.CONTACT.getTitle(), "contact.href", "contact.label"),
  APPOINTMENT(PAGES.APPOINTMENT.getTitle(), "appointment.href", "appointment.label"),
  OPENING_HOURS(PAGES.OPENING_HOURS.getTitle(), "hours.href", "hours.label");

  private String title;
  private String href;
  private String label;

  private MENUS(String title, String href, String label) {
    this.title = title;
    this.label = label;
    this.href = href;
  }

  /**
   * Retrouve le menu front associe a la page front via le titre
   * 
   * @param page
   * @return
   */
  public static MENUS getByPageTitle(PAGES page) {
    for (MENUS menu : MENUS.values()) {
      if (menu.getTitle().equals(page.getTitle())) {
        return menu;
      }
    }
    if (PAGES.NEWS_ENTRY.equals(page)) {
      return NEWS;
    }
    return null;
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
