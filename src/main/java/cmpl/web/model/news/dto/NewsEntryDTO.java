package cmpl.web.model.news.dto;

public class NewsEntryDTO extends BaseDTO {

  private NewsContentDTO newsContent;
  private NewsImageDTO newsImage;

  private String author;
  private String tags;
  private String title;

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public void setNewsContent(NewsContentDTO newsContent) {
    this.newsContent = newsContent;
  }

  public void setNewsImage(NewsImageDTO newsImage) {
    this.newsImage = newsImage;
  }

  public NewsContentDTO getNewsContent() {
    return newsContent;
  }

  public NewsImageDTO getNewsImage() {
    return newsImage;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getAuthor() {
    return author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
