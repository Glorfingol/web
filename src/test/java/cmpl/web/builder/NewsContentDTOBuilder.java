package cmpl.web.builder;

import java.time.LocalDate;

import cmpl.web.model.news.dto.NewsContentDTO;

public class NewsContentDTOBuilder {

  private String content;
  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;
  private String linkUrl;
  private String videoUrl;

  public NewsContentDTOBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public NewsContentDTOBuilder content(String content) {
    this.content = content;
    return this;
  }

  public NewsContentDTOBuilder linkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
    return this;
  }

  public NewsContentDTOBuilder videoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
    return this;
  }

  public NewsContentDTOBuilder creationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public NewsContentDTOBuilder modificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public NewsContentDTO toNewsContentDTO() {

    NewsContentDTO newsContentDTO = new NewsContentDTO();
    newsContentDTO.setId(id);
    newsContentDTO.setCreationDate(creationDate);
    newsContentDTO.setModificationDate(modificationDate);
    newsContentDTO.setContent(content);
    newsContentDTO.setLinkUrl(linkUrl);
    newsContentDTO.setVideoUrl(videoUrl);

    return newsContentDTO;
  }

}
