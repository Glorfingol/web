package com.cmpl.web.core.media;

import com.cmpl.web.core.common.dao.BaseDAO;

public interface MediaDAO extends BaseDAO<Media> {

  Media findByName(String name);

}
