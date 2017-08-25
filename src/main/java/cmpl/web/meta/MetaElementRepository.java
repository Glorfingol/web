package cmpl.web.meta;

import java.util.List;

import org.springframework.stereotype.Repository;

import cmpl.web.core.repository.BaseRepository;

@Repository
public interface MetaElementRepository extends BaseRepository<MetaElement> {

  List<MetaElement> findByPageId(String pageId);

}
