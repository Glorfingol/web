package cmpl.web.meta;

import java.util.List;

import cmpl.web.core.service.BaseService;

public interface MetaElementService extends BaseService<MetaElementDTO> {

  List<MetaElementDTO> findMetaElementsByPageId(String pageId);

}
