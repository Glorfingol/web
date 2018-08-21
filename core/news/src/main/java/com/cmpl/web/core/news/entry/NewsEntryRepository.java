package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.NewsEntry;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Repository des NewsEntry
 *
 * @author Louis
 */
@Repository
public interface NewsEntryRepository extends BaseRepository<NewsEntry> {

  /**
   * Recupere les entree dont l'id facebook correspond a facebookId
   */
  List<NewsEntry> findByFacebookId(String facebookId);

}
