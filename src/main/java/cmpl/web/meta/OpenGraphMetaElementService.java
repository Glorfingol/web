package cmpl.web.meta;

import java.util.List;

import cmpl.web.core.service.BaseService;

public interface OpenGraphMetaElementService extends BaseService<OpenGraphMetaElementDTO> {

  List<OpenGraphMetaElementDTO> findOpenGraphMetaElementsByPageId(String pageId);

}
