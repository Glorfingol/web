package com.cmpl.web.builder;

import java.time.LocalDate;

import com.cmpl.web.news.NewsContentRequest;
import com.cmpl.web.news.NewsEntryRequest;
import com.cmpl.web.news.NewsImageRequest;

public class NewsEntryRequestBuilder {

  private String author;
  private String tags;
  private String title;
  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;

  private NewsContentRequest content;
  private NewsImageRequest image;

  public NewsEntryRequestBuilder author(String author) {
    this.author = author;
    return this;
  }

  public NewsEntryRequestBuilder tags(String tags) {
    this.tags = tags;
    return this;
  }

  public NewsEntryRequestBuilder title(String title) {
    this.title = title;
    return this;
  }

  public NewsEntryRequestBuilder content(NewsContentRequest content) {
    this.content = content;
    return this;
  }

  public NewsEntryRequestBuilder image(NewsImageRequest image) {
    this.image = image;
    return this;
  }

  public NewsEntryRequestBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public NewsEntryRequestBuilder creationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public NewsEntryRequestBuilder modificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public NewsEntryRequest toNewsEntryRequest() {
    NewsEntryRequest request = new NewsEntryRequest();

    request.setId(id);
    request.setContent(content);
    request.setCreationDate(creationDate);
    request.setModificationDate(modificationDate);
    request.setAuthor(author);
    request.setTags(tags);
    request.setTitle(title);
    request.setImage(image);

    return request;
  }

}
