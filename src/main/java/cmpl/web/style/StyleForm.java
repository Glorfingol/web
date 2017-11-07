package cmpl.web.style;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class StyleForm {

  private String content;
  private Long id;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate creationDate;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate modificationDate;

  public StyleForm(StyleDTO style) {
    this.content = style.getContent();
    this.id = style.getId();
    this.creationDate = style.getCreationDate();
    this.modificationDate = style.getModificationDate();
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

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDate getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
  }

}