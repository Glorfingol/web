package cmpl.web.page;

/**
 * Enumeration pour les pages du front office du site
 * 
 * @author Louis
 *
 */
public enum PAGES {

  APPOINTMENT("appointment.tile", "appointment.title", "appointment.description", false),
  NEWS("news.tile", "news.title", "news.description", false),
  NEWS_ENTRY("news.entry.tile", "news.entry.title", "news.entry.description", false),
  INDEX("index.tile", "index.title", "index.description", true),
  OPENING_HOURS("hours.tile", "hours.title", "hours.description", false),
  CENTER("center.tile", "center.title", "center.description", false),
  PRICES("prices.tile", "prices.title", "prices.description", false),
  CONTACT("contact.tile", "contact.title", "contact.description", false),

  MEDICAL_CARE("medical.care.tile", "medical.care.title", "medical.care.description", false),
  GYNECOLOGIST("gynecologist.tile", "gynecologist.title", "gynecologist.description", false),
  TECHNICS("technics.tile", "technics.title", "technics.description", false);

  private String tileName;
  private String title;
  private String description;
  private boolean withCarousel;

  private PAGES(String tileName, String title, String description, boolean withCarousel) {
    this.tileName = tileName;
    this.title = title;
    this.description = description;
    this.withCarousel = withCarousel;
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

  public boolean isWithCarousel() {
    return withCarousel;
  }

}
