package cmpl.web.model.news.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "newsImage")
@Table(name = "news_image")
public class NewsImage extends BaseEntity {

  @Column(name = "src")
  private byte[] src;
  @Column(name = "legend")
  private String legend;
  @Column(name = "width")
  private int width;
  @Column(name = "height")
  private int height;
  @Column(name = "alt")
  private String alt;
  @Column(name = "format")
  private String format;

  public byte[] getSrc() {
    return src;
  }

  public void setSrc(byte[] src) {
    this.src = src;
  }

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

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

}
