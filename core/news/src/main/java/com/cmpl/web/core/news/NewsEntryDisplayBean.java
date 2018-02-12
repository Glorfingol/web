package com.cmpl.web.core.news;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
  private final DateTimeFormatter dateFormat;
  private static final String SPACE = " ";
  private static final String SEMICOLON = ";";

  /**
   * Constructeur a partir d'une NewsEntry, de labels et d'un format de date
   * 
   * @param newsEntryDTO
   * @param labelPar
   * @param labelLe
   * @param dateFormat
   */
  public NewsEntryDisplayBean(NewsEntryDTO newsEntryDTO, String labelPar, String labelLe, DateTimeFormatter dateFormat) {
    this.newsEntryDTO = newsEntryDTO;
    this.labelLe = labelLe;
    this.labelPar = labelPar;
    this.dateFormat = dateFormat;
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
    return newsEntryDTO.getNewsImage().getMedia().getSrc();
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

  boolean displayImage() {
    return newsEntryDTO.getNewsImage() != null && newsEntryDTO.getNewsImage().getMedia() != null;
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
    return dateFormat.format(newsEntryDTO.getCreationDate());
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

  public String getLinkUrl() {
    return newsEntryDTO.getNewsContent().getLinkUrl();
  }

  public String getVideoUrl() {
    return newsEntryDTO.getNewsContent().getVideoUrl();
  }
}
