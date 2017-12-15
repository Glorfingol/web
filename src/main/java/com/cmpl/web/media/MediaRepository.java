package com.cmpl.web.media;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.repository.BaseRepository;

@Repository
public interface MediaRepository extends BaseRepository<Media> {

  Media findByName(String name);

}
