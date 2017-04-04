package cmpl.web.model.news.display;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cmpl.web.model.news.dto.NewsEntryDTO;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class NewsEntryDisplayBean {

  private final NewsEntryDTO newsEntryDTO;
  private final String labelPar;
  private final String labelLe;
  private final String dateFormat;
  private static final String SPACE = " ";
  private static final String SEMICOLON = ";";

  public NewsEntryDisplayBean(NewsEntryDTO newsEntryDTO, String labelPar, String labelLe, String dateFormat) {
    this.newsEntryDTO = newsEntryDTO;
    this.labelLe = labelLe;
    this.labelPar = labelPar;
    this.dateFormat = dateFormat;
  }

  public List<String> getTags() {
    if (StringUtils.isEmpty(newsEntryDTO.getTags())) {
      return new ArrayList<String>();
    }
    return Lists.newArrayList(Splitter.on(SEMICOLON).split(newsEntryDTO.getTags()));
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

  private boolean displayImage() {
    return newsEntryDTO.getNewsImage() != null;
  }

  private boolean displayContent() {
    return newsEntryDTO.getNewsContent() != null;
  }

  public String getPanelHeading() {
    StringBuilder panelHeadingBuilder = new StringBuilder();
    panelHeadingBuilder.append(labelPar).append(SPACE).append(newsEntryDTO.getAuthor()).append(SPACE).append(labelLe)
        .append(SPACE).append(getPublicationDate());
    return panelHeadingBuilder.toString();
  }

  private String getPublicationDate() {
    SimpleDateFormat formatted = new SimpleDateFormat(dateFormat);
    return formatted.format(newsEntryDTO.getCreationDate());
  }

  public boolean isDisplayImage() {
    return displayImage();
  }

  public boolean isDisplayContent() {
    return displayContent();
  }

  public boolean isDisplayTags() {
    return !CollectionUtils.isEmpty(getTags());
  }
}
