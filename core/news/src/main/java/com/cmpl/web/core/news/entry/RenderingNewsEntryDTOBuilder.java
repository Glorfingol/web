package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.common.builder.BaseBuilder;
import org.springframework.util.StringUtils;

public class RenderingNewsEntryDTOBuilder extends BaseBuilder<RenderingNewsEntryDTO> {

  private String author;

  private String tags;

  private String title;

  private String name;

  private String uploadedImageSrc;

  private String legend;

  private String alt;

  private String editorialContent;

  private String editorialVideoUrl;

  private String editorialImageUrl;

  private RenderingNewsEntryDTOBuilder() {

  }

  public RenderingNewsEntryDTOBuilder author(String author) {
    this.author = author;
    return this;
  }

  public RenderingNewsEntryDTOBuilder title(String title) {
    this.title = title;
    return this;
  }


  public RenderingNewsEntryDTOBuilder name(String name) {
    this.name = name;
    return this;
  }


  public RenderingNewsEntryDTOBuilder uploadedImageSrc(String uploadedImageSrc) {
    this.uploadedImageSrc = uploadedImageSrc;
    return this;
  }


  public RenderingNewsEntryDTOBuilder legend(String legend) {
    this.legend = legend;
    return this;
  }


  public RenderingNewsEntryDTOBuilder alt(String alt) {
    this.alt = alt;
    return this;
  }

  public RenderingNewsEntryDTOBuilder editorialContent(String editorialContent) {
    this.editorialContent = editorialContent;
    return this;
  }

  public RenderingNewsEntryDTOBuilder editorialVideoUrl(String editorialVideoUrl) {
    this.editorialVideoUrl = editorialVideoUrl;
    return this;
  }

  public RenderingNewsEntryDTOBuilder editorialImageUrl(String editorialImageUrl) {
    this.editorialImageUrl = editorialImageUrl;
    return this;
  }

  public RenderingNewsEntryDTOBuilder tags(String tags) {
    this.tags = tags;
    return this;
  }


  @Override
  public RenderingNewsEntryDTO build() {
    RenderingNewsEntryDTO renderingNewsEntryDTO = new RenderingNewsEntryDTO();
    renderingNewsEntryDTO.setId(id);
    renderingNewsEntryDTO.setCreationDate(creationDate);
    renderingNewsEntryDTO.setModificationDate(modificationDate);
    renderingNewsEntryDTO.setAlt(alt);
    renderingNewsEntryDTO.setAuthor(author);
    renderingNewsEntryDTO.setEditorialContent(editorialContent);
    renderingNewsEntryDTO.setEditorialLink(editorialImageUrl);
    renderingNewsEntryDTO.setEditorialVideoUrl(editorialVideoUrl);
    renderingNewsEntryDTO.setLegend(legend);
    renderingNewsEntryDTO.setName(name);
    renderingNewsEntryDTO.setTags(tags);
    renderingNewsEntryDTO.setTitle(title);
    renderingNewsEntryDTO.setUploadedImageSrc(uploadedImageSrc);
    renderingNewsEntryDTO.setWithEditorialContent(
      StringUtils.hasText(editorialContent) || StringUtils.hasText(editorialImageUrl)
        || StringUtils.hasText(editorialVideoUrl));
    renderingNewsEntryDTO.setWithUploadedImage(StringUtils.hasText(uploadedImageSrc));

    return renderingNewsEntryDTO;
  }


  public static RenderingNewsEntryDTOBuilder create() {
    return new RenderingNewsEntryDTOBuilder();
  }
}
