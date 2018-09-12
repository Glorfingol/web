package com.cmpl.web.core.style;

import com.cmpl.web.core.common.form.BaseUpdateForm;

public class StyleUpdateForm extends BaseUpdateForm<StyleDTO> {

  private String content;

  private String name;

  private Long id;


  private String mediaName;

  private Long mediaId;


  public StyleUpdateForm() {

  }

  public StyleUpdateForm(StyleDTO style) {
    super(style);
    this.content = style.getContent();
    this.mediaId = style.getMedia().getId();
    this.mediaName = style.getMedia().getName();
    this.name = style.getName();
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getMediaName() {
    return mediaName;
  }

  public void setMediaName(String mediaName) {
    this.mediaName = mediaName;
  }

  public Long getMediaId() {
    return mediaId;
  }

  public void setMediaId(Long mediaId) {
    this.mediaId = mediaId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
