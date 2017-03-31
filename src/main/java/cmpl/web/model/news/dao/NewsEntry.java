package cmpl.web.model.news.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "newsEntry")
@Table(name = "news_entry")
public class NewsEntry extends BaseEntity {

  @Column(name = "author")
  private String author;
  @Column(name = "tag")
  private String tag;
  @Column(name = "image_id")
  private String imageId;
  @Column(name = "content_id")
  private String contentId;

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getImageId() {
    return imageId;
  }

  public void setImageId(String imageId) {
    this.imageId = imageId;
  }

  public String getContentId() {
    return contentId;
  }

  public void setContentId(String contentId) {
    this.contentId = contentId;
  }

}
