package cmpl.web.builder;

import java.util.Date;

import cmpl.web.model.news.dto.NewsContentDTO;

public class NewsContentDTOBuilder {

  private String content;
  private Long id;
  private Date creationDate;
  private Date modificationDate;

  public NewsContentDTOBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public NewsContentDTOBuilder content(String content) {
    this.content = content;
    return this;
  }

  public NewsContentDTOBuilder creationDate(Date creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public NewsContentDTOBuilder modificationDate(Date modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public NewsContentDTO toNewsContentDTO() {

    NewsContentDTO newsContentDTO = new NewsContentDTO();
    newsContentDTO.setId(id);
    newsContentDTO.setCreationDate(creationDate);
    newsContentDTO.setModificationDate(modificationDate);
    newsContentDTO.setContent(content);

    return newsContentDTO;
  }

}
