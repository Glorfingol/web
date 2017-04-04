package cmpl.web.model.news.dto;

public class NewsImageDTO extends BaseDTO {

  private String src;
  private String legend;
  private int width;
  private int height;
  private String alt;

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

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

}
