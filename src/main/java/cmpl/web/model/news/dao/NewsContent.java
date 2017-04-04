package cmpl.web.model.news.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "newsContent")
@Table(name = "news_content")
public class NewsContent extends BaseEntity {

  @Column(name = "content")
  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
