package cmpl.web.meta;

import java.util.List;

import org.springframework.stereotype.Repository;

import cmpl.web.core.repository.BaseRepository;

@Repository
public interface OpenGraphMetaElementRepository extends BaseRepository<OpenGraphMetaElement> {

  List<OpenGraphMetaElement> findByPageId(String pageId);

}
