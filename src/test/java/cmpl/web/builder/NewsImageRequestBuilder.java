package cmpl.web.builder;

import java.time.LocalDate;

import cmpl.web.news.NewsImageRequest;

public class NewsImageRequestBuilder {

  private String src;
  private String legend;
  private String alt;
  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;

  public NewsImageRequestBuilder src(String src) {
    this.src = src;
    return this;
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

  public NewsImageRequest toNewsImageRequest() {
    NewsImageRequest imageRequest = new NewsImageRequest();

    imageRequest.setAlt(alt);
    imageRequest.setId(id);
    imageRequest.setCreationDate(creationDate);
    imageRequest.setModificationDate(modificationDate);
    imageRequest.setLegend(legend);
    imageRequest.setSrc(src);

    return imageRequest;
  }

}
