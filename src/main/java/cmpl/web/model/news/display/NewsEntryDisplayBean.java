package cmpl.web.model.news.display;

import java.text.SimpleDateFormat;

import cmpl.web.model.news.dto.NewsEntryDTO;

public class NewsEntryDisplayBean {

  private NewsEntryDTO newsEntryDTO;

  private static final String DAY_MONTH_YEAR_FORMAT = "dd/MM/yy";

  public NewsEntryDisplayBean(NewsEntryDTO newsEntryDTO) {
    this.newsEntryDTO = newsEntryDTO;
  }

  public String getPublicationDate() {
    SimpleDateFormat formatted = new SimpleDateFormat(DAY_MONTH_YEAR_FORMAT);
    return formatted.format(newsEntryDTO.getCreationDate());
  }

  public String getTag() {
    return newsEntryDTO.getTag();
  }

  public String getAuthor() {
    return newsEntryDTO.getAuthor();
  }

  public String getTitle() {
    if (newsEntryDTO.getNewsContent() == null) {
      return "";
    }
    return newsEntryDTO.getNewsContent().getTitle();
  }

  public String getContent() {
    if (newsEntryDTO.getNewsContent() == null) {
      return "";
    }
    return newsEntryDTO.getNewsContent().getContent();
  }

  public String getImage() {
    if (newsEntryDTO.getNewsImage() == null) {
      return "";
    }
    return newsEntryDTO.getNewsImage().getSrc();
  }

  public String getLegend() {
    if (newsEntryDTO.getNewsImage() == null) {
      return "";
    }
    return newsEntryDTO.getNewsImage().getLegend();
  }

  public int getImageWitdh() {
    if (newsEntryDTO.getNewsImage() == null) {
      return 0;
    }
    return newsEntryDTO.getNewsImage().getWidth();
  }

  public int getImageHeight() {
    if (newsEntryDTO.getNewsImage() == null) {
      return 0;
    }
    return newsEntryDTO.getNewsImage().getHeight();
  }
}
