package cmpl.web.model.news.display;

import java.util.Date;

import cmpl.web.model.news.dto.NewsEntryDTO;

public class NewsEditBean {

  private final NewsEntryDTO newsEntryDTO;

  public NewsEditBean(NewsEntryDTO newsEntryDTO) {
    this.newsEntryDTO = newsEntryDTO;
  }

  public NewsEntryDTO getNewsEntryDTO() {
    return newsEntryDTO;
  }

  public String getAuthor() {
    return newsEntryDTO.getAuthor();
  }

  public String getTags() {
    return newsEntryDTO.getTags();
  }

  public String getTitle() {
    return newsEntryDTO.getTitle();
  }

  public byte[] getSrc() {
    return newsEntryDTO.getNewsImage().getSrc();
  }

  public String getLegend() {
    return newsEntryDTO.getNewsImage().getLegend();
  }

  public String getAlt() {
    return newsEntryDTO.getNewsImage().getAlt();
  }

  public String getBase64Src() {
    return newsEntryDTO.getNewsImage().getBase64Src();
  }

  public String getContent() {
    return newsEntryDTO.getNewsContent().getContent();
  }

  public Long getEntryId() {
    return newsEntryDTO.getId();
  }

  public Date getEntryCreationDate() {
    return newsEntryDTO.getCreationDate();
  }

  public Date getEntryModificationDate() {
    return newsEntryDTO.getModificationDate();
  }

  public Long getContentId() {
    return newsEntryDTO.getNewsContent().getId();
  }

  public Date getContentCreationDate() {
    return newsEntryDTO.getNewsContent().getCreationDate();
  }

  public Date getContentModificationDate() {
    return newsEntryDTO.getNewsContent().getModificationDate();
  }

  public Long getImageId() {
    return newsEntryDTO.getNewsImage().getId();
  }

  public Date getImageCreationDate() {
    return newsEntryDTO.getNewsImage().getCreationDate();
  }

  public Date getImageModificationDate() {
    return newsEntryDTO.getNewsImage().getModificationDate();
  }

}
