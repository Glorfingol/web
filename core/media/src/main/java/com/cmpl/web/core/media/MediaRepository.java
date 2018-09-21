package com.cmpl.web.core.media;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Media;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends BaseRepository<Media> {

  Media findByName(String name);

}
