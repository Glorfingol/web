package cmpl.web.model.news.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
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

}
