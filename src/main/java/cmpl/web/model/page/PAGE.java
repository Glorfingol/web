package cmpl.web.model.page;

public enum PAGE {

  APPOINTMENT("appointment.tile", "appointment.title", "appointment.description"),
  NEWS("news.tile", "news.title", "news.description"),
  NEWS_ENTRY("news.entry.tile", "news.entry.title", "news.entry.description"),
  INDEX("index.tile", "index.title", "index.description"),
  OPENING_HOURS("hours.tile", "hours.title", "hours.description"),
  CENTER("center.tile", "center.title", "center.description"),
  PRICES("prices.tile", "prices.title", "prices.description"),
  CONTACT("contact.tile", "contact.title", "contact.description"),

  MEDICAL_CARE("medical.care.tile", "medical.care.title", "medical.care.description"),
  GYNECOLOGIST("gynecologist.tile", "gynecologist.title", "gynecologist.description"),
  TECHNICS("technics.tile", "technics.title", "technics.description");

  private String tileName;
  private String title;
  private String description;

  private PAGE(String tileName, String title, String description) {
    this.tileName = tileName;
    this.title = title;
    this.description = description;
  }

  public String getTileName() {
    return tileName;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

}
