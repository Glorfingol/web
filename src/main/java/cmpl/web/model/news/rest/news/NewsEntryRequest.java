package cmpl.web.model.news.rest.news;

import java.util.Date;

public class NewsEntryRequest {

  private String author;
  private String tags;
  private String title;
  private Long id;
  private Date creationDate;
  private Date modificationDate;

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

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Date getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(Date modificationDate) {
    this.modificationDate = modificationDate;
  }

}
