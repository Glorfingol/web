package cmpl.web.model.page;

public enum PAGE {

  APPOINTMENT("appointment.tile", "appointment.title", "appointment.href", "appointment.label", "appointment.description"),
  NEWS("news.tile", "news.title", "news.href", "news.label", "news.description"),
  INDEX("index.tile", "index.title", "index.href", "index.label", "index.description"),
  MEDICAL_CARE("care.tile", "care.title", "care.href", "care.label", "care.description"),
  MEDICAL_CENTER("center.tile", "center.title", "center.href", "center.label", "center.description"),
  MEDICAL_LASERS("lasers.tile", "lasers.title", "lasers.href", "lasers.label", "lasers.description"),
  OPENING_HOURS("hours.tile", "hours.title", "hours.href", "hours.label", "hours.description"),
  PRICES("prices.tile", "prices.title", "prices.href", "prices.label", "prices.description");

  private String tileName;
  private String title;
  private String href;
  private String label;
  private String description;

  private PAGE(String tileName, String title, String href, String label, String description) {
    this.tileName = tileName;
    this.title = title;
    this.href = href;
    this.label = label;
    this.description = description;
  }

  public String getTileName() {
    return tileName;
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

  public String getDescription() {
    return description;
  }

}
