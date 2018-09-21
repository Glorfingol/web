package com.cmpl.web.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * DAO NewsContent
 *
 * @author Louis
 */
@Entity(name = "newsContent")
@Table(name = "news_content")
public class NewsContent extends BaseEntity {

  @Column(name = "content", columnDefinition = "blob")
  private String content;

  @Column(name = "videoUrl")
  private String videoUrl;

  @Column(name = "linkUrl")
  private String linkUrl;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public String getLinkUrl() {
    return linkUrl;
  }

  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
  }

}
