package com.cmpl.web.news;

import java.time.LocalDate;

import com.cmpl.web.core.builder.Builder;

public class NewsContentRequestBuilder extends Builder<NewsContentRequest> {

  private String content;
  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;

  private NewsContentRequestBuilder() {

  }

  public NewsContentRequestBuilder content(String content) {
    this.content = content;
    return this;
  }

  public NewsContentRequestBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public NewsContentRequestBuilder creationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public NewsContentRequestBuilder modificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  @Override
  public NewsContentRequest build() {
    NewsContentRequest newsContentRequest = new NewsContentRequest();

    newsContentRequest.setId(id);
    newsContentRequest.setCreationDate(creationDate);
    newsContentRequest.setModificationDate(modificationDate);
    newsContentRequest.setContent(content);
    return newsContentRequest;
  }

  public static NewsContentRequestBuilder create() {
    return new NewsContentRequestBuilder();
  }

}
