package com.cmpl.web.page;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.repository.BaseRepository;

@Repository
public interface PageRepository extends BaseRepository<Page> {

  /**
   * Trouver une page par son nom
   * 
   * @param name
   * @return
   */
  Page findByName(String name);

}
