package cmpl.web.model.news.dto;

public class NewsImageDTO extends BaseDTO {

  private byte[] src;
  private String legend;
  private int width;
  private int height;
  private String alt;
  private String base64Src;
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

  public String getBase64Src() {
    return base64Src;
  }

  public void setBase64Src(String base64Src) {
    this.base64Src = base64Src;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

}
