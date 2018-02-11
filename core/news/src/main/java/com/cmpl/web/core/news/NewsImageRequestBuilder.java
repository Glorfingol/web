package com.cmpl.web.core.news;

import java.time.LocalDate;

import com.cmpl.web.core.common.builder.Builder;

public class NewsImageRequestBuilder extends Builder<NewsImageRequest> {

  private String legend;
  private String alt;
  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;

  private NewsImageRequestBuilder() {

  }

  public NewsImageRequestBuilder legend(String legend) {
    this.legend = legend;
    return this;
  }

  public NewsImageRequestBuilder alt(String alt) {
    this.alt = alt;
    return this;
  }

  public NewsImageRequestBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public NewsImageRequestBuilder creationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public NewsImageRequestBuilder modificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  @Override
  public NewsImageRequest build() {
    NewsImageRequest imageRequest = new NewsImageRequest();

    imageRequest.setAlt(alt);
    imageRequest.setId(id);
    imageRequest.setCreationDate(creationDate);
    imageRequest.setModificationDate(modificationDate);
    imageRequest.setLegend(legend);

    return imageRequest;
  }

  public static NewsImageRequestBuilder create() {
    return new NewsImageRequestBuilder();
  }

}
