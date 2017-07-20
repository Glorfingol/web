package cmpl.web.model.news.rest.news;

import java.time.LocalDate;

/**
 * Requete pour les NewsContent
 * 
 * @author Louis
 *
 */
public class NewsContentRequest {

  private String content;
  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDate getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
