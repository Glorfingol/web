package cmpl.web.builder;

import java.util.Date;

import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.dto.NewsImageDTO;

public class NewsEntryDTOBuilder {

  private Long id;
  private Date creationDate;
  private Date modificationDate;

  private NewsContentDTO newsContent;
  private NewsImageDTO newsImage;

  private String author;
  private String tags;
  private String title;

  public NewsEntryDTOBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public NewsEntryDTOBuilder creationDate(Date creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public NewsEntryDTOBuilder modificationDate(Date modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public NewsEntryDTOBuilder newsContent(NewsContentDTO newsContent) {
    this.newsContent = newsContent;
    return this;
  }

  public NewsEntryDTOBuilder newsImage(NewsImageDTO newsImage) {
    this.newsImage = newsImage;
    return this;
  }

  public NewsEntryDTOBuilder author(String author) {
    this.author = author;
    return this;
  }

  public NewsEntryDTOBuilder tags(String tags) {
    this.tags = tags;
    return this;
  }

  public NewsEntryDTOBuilder title(String title) {
    this.title = title;
    return this;
  }

  public NewsEntryDTO toNewsEntryDTO() {
    NewsEntryDTO newsEntryDTO = new NewsEntryDTO();

    newsEntryDTO.setId(id);
    newsEntryDTO.setCreationDate(creationDate);
    newsEntryDTO.setModificationDate(modificationDate);
    newsEntryDTO.setAuthor(author);
    newsEntryDTO.setTags(tags);
    newsEntryDTO.setTitle(title);
    newsEntryDTO.setNewsContent(newsContent);
    newsEntryDTO.setNewsImage(newsImage);

    return newsEntryDTO;
  }

}
