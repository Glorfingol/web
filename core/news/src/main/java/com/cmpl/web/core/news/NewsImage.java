package com.cmpl.web.core.news;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

/**
 * DAO NewsImage
 * 
 * @author Louis
 *
 */
@Entity(name = "newsImage")
@Table(name = "news_image")
public class NewsImage extends BaseEntity {

  @Column(name = "src")
  private String src;
  @Column(name = "legend")
  private String legend;
  @Column(name = "width")
  private int width;
  @Column(name = "height")
  private int height;
  @Column(name = "alt")
  private String alt;

  public String getLegend() {
    return legend;
  }

  public void setLegend(String legend) {
    this.legend = legend;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

}
