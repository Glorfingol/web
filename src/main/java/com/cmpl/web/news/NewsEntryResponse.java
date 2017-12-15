package com.cmpl.web.news;

import com.cmpl.web.core.model.BaseResponse;

/**
 * Reponse NewsEntry
 * 
 * @author Louis
 *
 */
public class NewsEntryResponse extends BaseResponse {

  private NewsEntryDTO newsEntry;

  public NewsEntryDTO getNewsEntry() {
    return newsEntry;
  }

  public void setNewsEntry(NewsEntryDTO newsEntry) {
    this.newsEntry = newsEntry;
  }

}
