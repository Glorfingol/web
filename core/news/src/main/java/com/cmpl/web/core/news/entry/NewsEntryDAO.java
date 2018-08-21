package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.NewsEntry;
import java.util.List;

public interface NewsEntryDAO extends BaseDAO<NewsEntry> {

  List<NewsEntry> findByFacebookId(String facebookId);
}
