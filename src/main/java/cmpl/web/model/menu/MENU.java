package cmpl.web.model.menu;

import cmpl.web.model.page.PAGE;

public enum MENU {

  INDEX(PAGE.INDEX.getTitle(), "index.href", "index.label"),
  APPOINTMENT(PAGE.APPOINTMENT.getTitle(), "appointment.href", "appointment.label"),
  NEWS(PAGE.NEWS.getTitle(), "news.href", "news.label"),
  OPENING_HOURS(PAGE.OPENING_HOURS.getTitle(), "hours.href", "hours.label"),
  PRICES(PAGE.PRICES.getTitle(), "prices.href", "prices.label"),
  CENTER(PAGE.CENTER.getTitle(), "center.href", "center.label"),

  FACIAL_INJECTION(PAGE.FACIAL_INJECTION.getTitle(), "facial.injection.href", "facial.injection.label"),
  LASER_HAIR_REMOVAL(PAGE.LASER_HAIR_REMOVAL.getTitle(), "laser.hair.removal.href", "laser.hair.removal.label"),
  LASER_TREATMENT(PAGE.LASER_TREATMENT.getTitle(), "laser.treatment.href", "laser.treatment.label"),
  THINNING(PAGE.THINNING.getTitle(), "thinning.href", "thinning.label"),
  PEELING(PAGE.PEELING.getTitle(), "peeling.href", "peeling.label"),
  GENITAL_AESTHETIC(PAGE.GENITAL_AESTHETIC.getTitle(), "genital.aesthetic.href", "genital.aesthetic.label");

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
