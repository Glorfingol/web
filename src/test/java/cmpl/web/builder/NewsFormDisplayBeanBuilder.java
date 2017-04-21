package cmpl.web.builder;

import cmpl.web.model.news.display.NewsFormDisplayBean;

public class NewsFormDisplayBeanBuilder {

  private String titleLabel;
  private String titleHelp;

  private String authorLabel;
  private String authorHelp;

  private String tagsLabel;
  private String tagsHelp;

  private String contentLabel;
  private String contentHelp;

  private String imageLabel;
  private String imageHelp;

  private String legendLabel;
  private String legendHelp;

  private String altLabel;
  private String altHelp;

  public NewsFormDisplayBeanBuilder titleLabel(String titleLabel) {
    this.titleLabel = titleLabel;
    return this;
  }

  public NewsFormDisplayBeanBuilder titleHelp(String titleHelp) {
    this.titleHelp = titleHelp;
    return this;
  }

  public NewsFormDisplayBeanBuilder authorLabel(String authorLabel) {
    this.authorLabel = authorLabel;
    return this;
  }

  public NewsFormDisplayBeanBuilder authorHelp(String authorHelp) {
    this.authorHelp = authorHelp;
    return this;
  }

  public NewsFormDisplayBeanBuilder tagsLabel(String tagsLabel) {
    this.tagsLabel = tagsLabel;
    return this;
  }

  public NewsFormDisplayBeanBuilder tagsHelp(String tagsHelp) {
    this.tagsHelp = tagsHelp;
    return this;
  }

  public NewsFormDisplayBeanBuilder contentLabel(String contentLabel) {
    this.contentLabel = contentLabel;
    return this;
  }

  public NewsFormDisplayBeanBuilder contentHelp(String contentHelp) {
    this.contentHelp = contentHelp;
    return this;
  }

  public NewsFormDisplayBeanBuilder imageLabel(String imageLabel) {
    this.imageLabel = imageLabel;
    return this;
  }

  public NewsFormDisplayBeanBuilder imageHelp(String imageHelp) {
    this.imageHelp = imageHelp;
    return this;
  }

  public NewsFormDisplayBeanBuilder legendLabel(String legendLabel) {
    this.legendLabel = legendLabel;
    return this;
  }

  public NewsFormDisplayBeanBuilder legendHelp(String legendHelp) {
    this.legendHelp = legendHelp;
    return this;
  }

  public NewsFormDisplayBeanBuilder altLabel(String altLabel) {
    this.altLabel = altLabel;
    return this;
  }

  public NewsFormDisplayBeanBuilder altHelp(String altHelp) {
    this.altHelp = altHelp;
    return this;
  }

  public NewsFormDisplayBean toNewsFormDisplayBean() {
    NewsFormDisplayBean newsFormDisplayBean = new NewsFormDisplayBean();

    newsFormDisplayBean.setAltHelp(altHelp);
    newsFormDisplayBean.setAltLabel(altLabel);
    newsFormDisplayBean.setAuthorHelp(authorHelp);
    newsFormDisplayBean.setAuthorLabel(authorLabel);
    newsFormDisplayBean.setContentHelp(contentHelp);
    newsFormDisplayBean.setContentLabel(contentLabel);
    newsFormDisplayBean.setImageHelp(imageHelp);
    newsFormDisplayBean.setImageLabel(imageLabel);
    newsFormDisplayBean.setLegendHelp(legendHelp);
    newsFormDisplayBean.setLegendLabel(legendLabel);
    newsFormDisplayBean.setTagsHelp(tagsHelp);
    newsFormDisplayBean.setTagsLabel(tagsLabel);
    newsFormDisplayBean.setTitleHelp(titleHelp);
    newsFormDisplayBean.setTitleLabel(titleLabel);

    return newsFormDisplayBean;
  }

}
