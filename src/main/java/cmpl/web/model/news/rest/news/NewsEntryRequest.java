package cmpl.web.model.news.rest.news;

import java.time.LocalDate;

/**
 * Requete NewsEntry
 * 
 * @author Louis
 *
 */
public class NewsEntryRequest {

  private String author;
  private String tags;
  private String title;
  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;

  private NewsContentRequest content;
  private NewsImageRequest image;

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public NewsContentRequest getContent() {
    return content;
  }

  public void setContent(NewsContentRequest content) {
    this.content = content;
  }

  public NewsImageRequest getImage() {
    return image;
  }

  public void setImage(NewsImageRequest image) {
    this.image = image;
  }

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

}
