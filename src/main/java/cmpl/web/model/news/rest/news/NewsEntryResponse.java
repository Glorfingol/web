package cmpl.web.model.news.rest.news;

import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.error.Error;
import cmpl.web.model.news.rest.BaseResponse;

public class NewsEntryResponse extends BaseResponse {

  private NewsEntryDTO newsEntry;
  private Error error;

  public NewsEntryDTO getNewsEntry() {
    return newsEntry;
  }

  public void setNewsEntry(NewsEntryDTO newsEntry) {
    this.newsEntry = newsEntry;
  }

  public Error getError() {
    return error;
  }

  public void setError(Error error) {
    this.error = error;
  }

}
