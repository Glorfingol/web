package cmpl.web.builder;

import java.time.LocalDate;

import cmpl.web.news.NewsImageDTO;

public class NewsImageDTOBuilder {

  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;

  private String src;
  private String legend;
  private int width;
  private int height;
  private String alt;
  private String base64Src;

  public NewsImageDTOBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public NewsImageDTOBuilder creationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public NewsImageDTOBuilder modificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public NewsImageDTOBuilder src(String src) {
    this.src = src;
    return this;
  }

  public NewsImageDTOBuilder legend(String legend) {
    this.legend = legend;
    return this;
  }

  public NewsImageDTOBuilder width(int width) {
    this.width = width;
    return this;
  }

  public NewsImageDTOBuilder height(int height) {
    this.height = height;
    return this;
  }

  public NewsImageDTOBuilder alt(String alt) {
    this.alt = alt;
    return this;
  }

  public NewsImageDTOBuilder base64Src(String base64Src) {
    this.base64Src = base64Src;
    return this;
  }

  public NewsImageDTO toNewsImageDTO() {

    NewsImageDTO newsImageDTO = new NewsImageDTO();
    newsImageDTO.setId(id);
    newsImageDTO.setCreationDate(creationDate);
    newsImageDTO.setModificationDate(modificationDate);
    newsImageDTO.setAlt(alt);
    newsImageDTO.setBase64Src(base64Src);
    newsImageDTO.setHeight(height);
    newsImageDTO.setLegend(legend);
    newsImageDTO.setSrc(src);
    newsImageDTO.setWidth(width);

    return newsImageDTO;
  }

}
