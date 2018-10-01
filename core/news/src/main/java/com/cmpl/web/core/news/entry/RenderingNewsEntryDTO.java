package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.common.dto.BaseDTO;

public class RenderingNewsEntryDTO extends BaseDTO {

  private String author;

  private String tags;

  private String title;

  private String name;

  private String uploadedImageSrc;

  private String legend;

  private String alt;

  private String editorialContent;

  private String editorialVideoUrl;

  private String editorialLink;

  private boolean withEditorialContent;

  private boolean withUploadedImage;


  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUploadedImageSrc() {
    return uploadedImageSrc;
  }

  public void setUploadedImageSrc(String uploadedImageSrc) {
    this.uploadedImageSrc = uploadedImageSrc;
  }

  public String getLegend() {
    return legend;
  }

  public void setLegend(String legend) {
    this.legend = legend;
  }

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

  public String getEditorialContent() {
    return editorialContent;
  }

  public void setEditorialContent(String editorialContent) {
    this.editorialContent = editorialContent;
  }

  public String getEditorialVideoUrl() {
    return editorialVideoUrl;
  }

  public void setEditorialVideoUrl(String editorialVideoUrl) {
    this.editorialVideoUrl = editorialVideoUrl;
  }

  public String getEditorialLink() {
    return editorialLink;
  }

  public void setEditorialLink(String editorialLink) {
    this.editorialLink = editorialLink;
  }

  public boolean isWithEditorialContent() {
    return withEditorialContent;
  }

  public void setWithEditorialContent(boolean withEditorialContent) {
    this.withEditorialContent = withEditorialContent;
  }

  public boolean isWithUploadedImage() {
    return withUploadedImage;
  }

  public void setWithUploadedImage(boolean withUploadedImage) {
    this.withUploadedImage = withUploadedImage;
  }
}
