package com.cmpl.web.core.website;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Website;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteRepository extends BaseRepository<Website> {

  Website findByName(String websiteName);

}
