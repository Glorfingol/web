package cmpl.web.meta;

import java.util.List;

public interface MetaElementService {

  List<MetaElementDTO> findMetaElementsByPageId(String pageId);

}
