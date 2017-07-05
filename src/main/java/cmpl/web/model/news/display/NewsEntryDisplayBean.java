package cmpl.web.model.news.display;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cmpl.web.model.news.dto.NewsEntryDTO;

/**
 * Bean pour l'affichage des actualites sur le site
 * 
 * @author Louis
 *
 */
public class NewsEntryDisplayBean {

  private final NewsEntryDTO newsEntryDTO;
  private final String labelPar;
  private final String labelLe;
  private final String labelAccroche;
  private final String dateFormat;
  private static final String SPACE = " ";
  private static final String SEMICOLON = ";";

  /**
   * Constructeur a partir d'une NewsEntry, de labels et d'un format de date
   * 
   * @param newsEntryDTO
   * @param labelPar
   * @param labelLe
   * @param dateFormat
   * @param labelAccroche
   */
  public NewsEntryDisplayBean(NewsEntryDTO newsEntryDTO, String labelPar, String labelLe, String dateFormat,
      String labelAccroche) {
    this.newsEntryDTO = newsEntryDTO;
    this.labelLe = labelLe;
    this.labelPar = labelPar;
    this.dateFormat = dateFormat;
    this.labelAccroche = labelAccroche;
  }

  public List<String> getTags() {
    if (!StringUtils.hasText(newsEntryDTO.getTags())) {
      return new ArrayList<>();
    }
    String[] splittedTags = newsEntryDTO.getTags().split(SEMICOLON);
    return Arrays.asList(splittedTags);
  }

  public String getTitle() {
    return newsEntryDTO.getTitle();
  }

  public String getContent() {
    if (!displayContent()) {
      return "";
    }
    return newsEntryDTO.getNewsContent().getContent();
  }

  public String getImage() {
    if (!displayImage()) {
      return "";
    }
    return newsEntryDTO.getNewsImage().getSrc();
  }

  public String getLegend() {
    if (!displayImage()) {
      return "";
    }
    return newsEntryDTO.getNewsImage().getLegend();
  }

  public String getAlt() {
    if (!displayImage()) {
      return "";
    }
    return newsEntryDTO.getNewsImage().getAlt();
  }

  public int getImageWidth() {
    if (!displayImage()) {
      return 0;
    }
    return newsEntryDTO.getNewsImage().getWidth();
  }

  public int getImageHeight() {
    if (!displayImage()) {
      return 0;
    }
    return newsEntryDTO.getNewsImage().getHeight();
  }

  boolean displayImage() {
    return newsEntryDTO.getNewsImage() != null;
  }

  boolean displayContent() {
    return newsEntryDTO.getNewsContent() != null;
  }

  public String getPanelHeading() {
    StringBuilder panelHeadingBuilder = new StringBuilder();
    panelHeadingBuilder.append(labelPar).append(SPACE).append(newsEntryDTO.getAuthor()).append(SPACE).append(labelLe)
        .append(SPACE).append(getPublicationDate());
    return panelHeadingBuilder.toString();
  }

  String getPublicationDate() {
    SimpleDateFormat formatted = new SimpleDateFormat(dateFormat);
    return formatted.format(newsEntryDTO.getCreationDate());
  }

  public boolean isDisplayImage() {
    return displayImage();
  }

  public boolean isDisplayContent() {
    return displayContent() && !StringUtils.hasText(newsEntryDTO.getNewsContent().getLinkUrl());
  }

  public boolean isDisplayTags() {
    return !CollectionUtils.isEmpty(getTags());
  }

  public boolean isDisplayLink() {
    return displayContent() && StringUtils.hasText(newsEntryDTO.getNewsContent().getLinkUrl());
  }

  public boolean isDisplayVideo() {
    return displayContent() && StringUtils.hasText(newsEntryDTO.getNewsContent().getVideoUrl());
  }

  public String getNewsEntryId() {
    return String.valueOf(newsEntryDTO.getId());
  }

  public String getNewsEntryModifyHref() {
    return "/manager/news/" + newsEntryDTO.getId();
  }

  public String getNewsEntryReadHref() {
    return "/news/" + newsEntryDTO.getId();
  }

  public String getNewsEntryShowHref() {
    return "/actualites/" + newsEntryDTO.getId();
  }

  public String getNewsEntryShowMore() {
    return labelAccroche;
  }

  public String getLinkUrl() {
    return newsEntryDTO.getNewsContent().getLinkUrl();
  }

  public String getVideoUrl() {
    return newsEntryDTO.getNewsContent().getVideoUrl();
  }
}
