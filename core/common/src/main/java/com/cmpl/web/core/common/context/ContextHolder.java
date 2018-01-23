package com.cmpl.web.core.common.context;

import java.time.format.DateTimeFormatter;

/**
 * Holder des donnees de configuration globales de l'application
 * 
 * @author Louis
 *
 */
public class ContextHolder {

  private DateTimeFormatter dateFormat;
  private String imageDisplaySrc;
  private String imageFileSrc;
  private String templateBasePath;
  private int elementsPerPage;
  private String mediaBasePath;
  private String mediaDisplayPath;

  public DateTimeFormatter getDateFormat() {
    return dateFormat;
  }

  public void setDateFormat(DateTimeFormatter dateFormat) {
    this.dateFormat = dateFormat;
  }

  public String getImageDisplaySrc() {
    return imageDisplaySrc;
  }

  public void setImageDisplaySrc(String imageDisplaySrc) {
    this.imageDisplaySrc = imageDisplaySrc;
  }

  public String getImageFileSrc() {
    return imageFileSrc;
  }

  public void setImageFileSrc(String imageFileSrc) {
    this.imageFileSrc = imageFileSrc;
  }

  public int getElementsPerPage() {
    return elementsPerPage;
  }

  public void setElementsPerPage(int elementsPerPage) {
    this.elementsPerPage = elementsPerPage;
  }

  public String getTemplateBasePath() {
    return templateBasePath;
  }

  public void setTemplateBasePath(String templateBasePath) {
    this.templateBasePath = templateBasePath;
  }

  public String getMediaBasePath() {
    return mediaBasePath;
  }

  public void setMediaBasePath(String mediaBasePath) {
    this.mediaBasePath = mediaBasePath;
  }

  public String getMediaDisplayPath() {
    return mediaDisplayPath;
  }

  public void setMediaDisplayPath(String mediaDisplayPath) {
    this.mediaDisplayPath = mediaDisplayPath;
  }

}
