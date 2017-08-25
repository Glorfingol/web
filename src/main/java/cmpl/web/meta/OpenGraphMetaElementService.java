package cmpl.web.meta;

import java.util.List;

public interface OpenGraphMetaElementService {

  List<OpenGraphMetaElementDTO> findOpenGraphMetaElementsByPageId(String pageId);

}
