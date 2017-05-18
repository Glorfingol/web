package cmpl.web.service;

import java.util.List;

import cmpl.web.model.BaseException;
import cmpl.web.model.facebook.ImportablePost;

public interface FacebookService {

  List<ImportablePost> getRecentFeed() throws BaseException;

}
