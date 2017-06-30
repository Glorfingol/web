package cmpl.web.model.meta;

/**
 * Objet representant un meta element pour les pages du site
 * 
 * @author Louis
 *
 */
public class MetaElement {

  private String name;
  private String content;
  private String property;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

}
