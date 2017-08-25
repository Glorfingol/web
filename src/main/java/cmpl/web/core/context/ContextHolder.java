package cmpl.web.core.context;

import java.time.format.DateTimeFormatter;

/**
 * Holder des donnees de configuration globales de l'application
 * 
 * @author Louis
 *
 */
public class ContextHolder {

  private DateTimeFormatter dateFormat;
  private String imageDisplaySrc;
  private String imageFileSrc;
  private int elementsPerPage;

  public DateTimeFormatter getDateFormat() {
    return dateFormat;
  }

  public void setDateFormat(DateTimeFormatter dateFormat) {
    this.dateFormat = dateFormat;
  }

  public String getImageDisplaySrc() {
    return imageDisplaySrc;
  }

  public void setImageDisplaySrc(String imageDisplaySrc) {
    this.imageDisplaySrc = imageDisplaySrc;
  }

  public String getImageFileSrc() {
    return imageFileSrc;
  }

  public void setImageFileSrc(String imageFileSrc) {
    this.imageFileSrc = imageFileSrc;
  }

  public int getElementsPerPage() {
    return elementsPerPage;
  }

  public void setElementsPerPage(int elementsPerPage) {
    this.elementsPerPage = elementsPerPage;
  }

}
