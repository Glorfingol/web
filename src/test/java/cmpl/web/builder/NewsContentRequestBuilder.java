package cmpl.web.builder;

import java.util.Date;

import cmpl.web.model.news.rest.news.NewsContentRequest;

public class NewsContentRequestBuilder {

  private String content;
  private Long id;
  private Date creationDate;
  private Date modificationDate;

  public NewsContentRequestBuilder content(String content) {
    this.content = content;
    return this;
  }

  public NewsContentRequestBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public NewsContentRequestBuilder creationDate(Date creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public NewsContentRequestBuilder modificationDate(Date modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public NewsContentRequest toNewsContentRequest() {
    NewsContentRequest newsContentRequest = new NewsContentRequest();

    newsContentRequest.setId(id);
    newsContentRequest.setCreationDate(creationDate);
    newsContentRequest.setModificationDate(modificationDate);
    newsContentRequest.setContent(content);
    return newsContentRequest;
  }

}
