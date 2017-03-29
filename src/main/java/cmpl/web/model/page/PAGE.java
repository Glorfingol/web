package cmpl.web.model.page;

public enum PAGE {

  APPOINTMENT("appointment.tile", "appointment.title", "appointment.href", "appointment.label"),
  NEWS("news.tile", "news.title", "news.href", "news.label"),
  INDEX("index.tile", "index.title", "index.href", "index.label"),
  MEDICAL_CARE("care.tile", "care.title", "care.href", "care.label"),
  MEDICAL_CENTER("center.tile", "center.title", "center.href", "center.label"),
  MEDICAL_LASERS("lasers.tile", "lasers.title", "lasers.href", "lasers.label"),
  OPENING_HOURS("hours.tile", "hours.title", "hours.href", "hours.label"),
  PRICES("prices.tile", "prices.title", "prices.href", "prices.label");

  private String tileName;
  private String title;
  private String href;
  private String label;

  private PAGE(String tileName, String title, String href, String label) {
    this.tileName = tileName;
    this.title = title;
    this.href = href;
    this.label = label;
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

}
