package cmpl.web.model.context;

import java.text.SimpleDateFormat;

/**
 * Holder des donnees de configuration globales de l'application
 * 
 * @author Louis
 *
 */
public class ContextHolder {

  private SimpleDateFormat dateFormat;
  private String imageDisplaySrc;
  private String imageFileSrc;
  private int elementsPerPage;

  public SimpleDateFormat getDateFormat() {
    return dateFormat;
  }

  public void setDateFormat(SimpleDateFormat dateFormat) {
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
