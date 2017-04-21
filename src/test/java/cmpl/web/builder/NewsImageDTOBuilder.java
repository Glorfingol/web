package cmpl.web.builder;

import java.util.Date;

import cmpl.web.model.news.dto.NewsImageDTO;

public class NewsImageDTOBuilder {

  private Long id;
  private Date creationDate;
  private Date modificationDate;

  private byte[] src;
  private String legend;
  private int width;
  private int height;
  private String alt;
  private String base64Src;
  private String format;

  public NewsImageDTOBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public NewsImageDTOBuilder creationDate(Date creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public NewsImageDTOBuilder modificationDate(Date modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public NewsImageDTOBuilder src(byte[] src) {
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

  public NewsImageDTOBuilder format(String format) {
    this.format = format;
    return this;
  }

  public NewsImageDTO toNewsImageDTO() {

    NewsImageDTO newsImageDTO = new NewsImageDTO();
    newsImageDTO.setId(id);
    newsImageDTO.setCreationDate(creationDate);
    newsImageDTO.setModificationDate(modificationDate);
    newsImageDTO.setAlt(alt);
    newsImageDTO.setBase64Src(base64Src);
    newsImageDTO.setFormat(format);
    newsImageDTO.setHeight(height);
    newsImageDTO.setLegend(legend);
    newsImageDTO.setSrc(src);
    newsImageDTO.setWidth(width);

    return newsImageDTO;
  }

}
