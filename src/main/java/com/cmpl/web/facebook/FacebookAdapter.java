package com.cmpl.web.facebook;

import java.util.List;

import org.springframework.social.facebook.api.MediaOperations;

public interface FacebookAdapter {

  public boolean isAlreadyConnected();

  public List<ImportablePost> getRecentFeed();

  public MediaOperations getMediaOperations();

}
