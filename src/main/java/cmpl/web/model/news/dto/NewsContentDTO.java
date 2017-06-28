package cmpl.web.model.news.dto;

/**
 * DTO NewsContent
 * 
 * @author Louis
 *
 */
public class NewsContentDTO extends BaseDTO {

  private String content;

  private String videoUrl;

  private String linkUrl;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public String getLinkUrl() {
    return linkUrl;
  }

  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
  }

}
