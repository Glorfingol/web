package com.cmpl.web.news;

import com.cmpl.web.core.builder.BaseBuilder;

public class NewsImageDTOBuilder extends BaseBuilder<NewsImageDTO> {

  private String src;
  private String legend;
  private int width;
  private int height;
  private String alt;
  private String base64Src;

  private NewsImageDTOBuilder() {

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

  @Override
  public NewsImageDTO build() {
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

  public static NewsImageDTOBuilder create() {
    return new NewsImageDTOBuilder();
  }

}
