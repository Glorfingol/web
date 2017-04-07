package cmpl.web.model.menu;

import cmpl.web.model.page.PAGE;

public enum MENU {

  INDEX(PAGE.INDEX.getTitle(), "index.href", "index.label"),
  CENTER(PAGE.CENTER.getTitle(), "center.href", "center.label"),
  GYNECOLOGIST(PAGE.GYNECOLOGIST.getTitle(), "gynecologist.href", "gynecologist.label"),
  MEDICAL_CARE(PAGE.MEDICAL_CARE.getTitle(), "medical.care.href", "medical.care.label"),
  TECHNICS(PAGE.TECHNICS.getTitle(), "technics.href", "technics.label"),
  PRICES(PAGE.PRICES.getTitle(), "prices.href", "prices.label"),
  NEWS(PAGE.NEWS.getTitle(), "news.href", "news.label"),
  CONTACT(PAGE.CONTACT.getTitle(), "contact.href", "contact.label"),
  APPOINTMENT(PAGE.APPOINTMENT.getTitle(), "appointment.href", "appointment.label"),
  OPENING_HOURS(PAGE.OPENING_HOURS.getTitle(), "hours.href", "hours.label");

  private String title;
  private String href;
  private String label;

  private MENU(String title, String href, String label) {
    this.title = title;
    this.label = label;
    this.href = href;
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
