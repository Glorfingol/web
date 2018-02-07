package com.cmpl.web.core.meta;

import java.util.List;

import com.cmpl.web.core.common.service.BaseService;

public interface MetaElementService extends BaseService<MetaElementDTO> {

  List<MetaElementDTO> findMetaElementsByPageId(String pageId);

  void deleteEntityInPage(String pageId, Long openGraphMetaId);

}
