package cmpl.web.builder;

import java.util.Date;

import cmpl.web.model.news.rest.news.NewsContentRequest;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsImageRequest;

public class NewsEntryRequestBuilder {

  private String author;
  private String tags;
  private String title;
  private Long id;
  private Date creationDate;
  private Date modificationDate;

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

  public NewsEntryRequestBuilder creationDate(Date creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public NewsEntryRequestBuilder modificationDate(Date modificationDate) {
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
