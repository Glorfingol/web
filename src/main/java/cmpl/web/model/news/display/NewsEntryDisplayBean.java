package cmpl.web.model.news.display;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import cmpl.web.model.news.dto.NewsEntryDTO;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

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

  public List<String> getTags() {
    if (StringUtils.isEmpty(newsEntryDTO.getTags())) {
      return new ArrayList<String>();
    }
    return Lists.newArrayList(Splitter.on(";").split(newsEntryDTO.getTags()));
  }

  public String getAuthor() {
    return newsEntryDTO.getAuthor();
  }

  public String getTitle() {
    if (!hasContent()) {
      return "";
    }
    return newsEntryDTO.getNewsContent().getTitle();
  }

  public String getContent() {
    if (!hasContent()) {
      return "";
    }
    return newsEntryDTO.getNewsContent().getContent();
  }

  public String getImage() {
    if (!hasImage()) {
      return "";
    }
    return newsEntryDTO.getNewsImage().getSrc();
  }

  public String getLegend() {
    if (!hasImage()) {
      return "";
    }
    return newsEntryDTO.getNewsImage().getLegend();
  }

  public int getImageWitdh() {
    if (!hasImage()) {
      return 0;
    }
    return newsEntryDTO.getNewsImage().getWidth();
  }

  public int getImageHeight() {
    if (!hasImage()) {
      return 0;
    }
    return newsEntryDTO.getNewsImage().getHeight();
  }

  public boolean hasImage() {
    return newsEntryDTO.getNewsImage() != null;
  }

  public boolean hasContent() {
    return newsEntryDTO.getNewsContent() != null;
  }

}
