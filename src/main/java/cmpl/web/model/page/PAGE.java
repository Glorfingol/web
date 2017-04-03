package cmpl.web.model.page;

public enum PAGE {

  APPOINTMENT("appointment.tile", "appointment.title", "appointment.description"),
  NEWS("news.tile", "news.title", "news.description"),
  INDEX("index.tile", "index.title", "index.description"),
  OPENING_HOURS("hours.tile", "hours.title", "hours.description"),
  CENTER("center.tile", "center.title", "center.description"),
  PRICES("prices.tile", "prices.title", "prices.description"),

  FACIAL_INJECTION("facial.injection.tile", "facial.injection.title", "facial.injection.description"),
  LASER_HAIR_REMOVAL("laser.hair.removal.tile", "laser.hair.removal.title", "laser.hair.removal.description"),
  LASER_TREATMENT("laser.treatment.tile", "laser.treatment.title", "laser.treatment.description"),
  THINNING("thinning.tile", "thinning.title", "thinning.description"),
  PEELING("peeling.tile", "peeling.title", "peeling.description"),
  GENITAL_AESTHETIC("genital.aesthetic.tile", "genital.aesthetic.title", "genital.aesthetic.description");

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
