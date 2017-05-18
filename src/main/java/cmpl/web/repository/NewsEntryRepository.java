package cmpl.web.repository;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import cmpl.web.model.news.dao.NewsEntry;

@Repository
public interface NewsEntryRepository extends BaseRepository<NewsEntry> {

  List<NewsEntry> findByCreationDateBetween(Date start, Date end);

  List<NewsEntry> findByFacebookId(String facebookId);

}
