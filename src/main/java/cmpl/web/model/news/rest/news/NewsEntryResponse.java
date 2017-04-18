package cmpl.web.model.news.rest.news;

import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.rest.BaseResponse;

public class NewsEntryResponse extends BaseResponse {

  private NewsEntryDTO newsEntry;

  public NewsEntryDTO getNewsEntry() {
    return newsEntry;
  }

  public void setNewsEntry(NewsEntryDTO newsEntry) {
    this.newsEntry = newsEntry;
  }

}
