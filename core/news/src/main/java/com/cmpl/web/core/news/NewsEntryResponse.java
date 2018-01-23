package com.cmpl.web.core.news;

import com.cmpl.web.core.common.resource.BaseResponse;

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
