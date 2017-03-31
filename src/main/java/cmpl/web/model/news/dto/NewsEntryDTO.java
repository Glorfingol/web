package cmpl.web.model.news.dto;

public class NewsEntryDTO extends BaseDTO {

  private NewsContentDTO newsContent;
  private NewsImageDTO newsImage;

  private String author;
  private String tag;

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
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

}
