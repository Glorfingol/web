package com.cmpl.web.core.page;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends BaseRepository<Page> {

  /**
   * Trouver une page par son nom
   */
  Page findByName(String name);

  List<Page> findByHref(String href);

}
