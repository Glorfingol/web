package cmpl.web.model.facebook;

import java.util.List;

import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.rest.BaseResponse;

public class FacebookImportResponse extends BaseResponse {

  private List<NewsEntryDTO> createdNewsEntries;

  public List<NewsEntryDTO> getCreatedNewsEntries() {
    return createdNewsEntries;
  }

  public void setCreatedNewsEntries(List<NewsEntryDTO> createdNewsEntries) {
    this.createdNewsEntries = createdNewsEntries;
  }

}
