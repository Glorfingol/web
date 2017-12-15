package com.cmpl.web.meta;

import java.util.List;

import com.cmpl.web.core.service.BaseService;

public interface MetaElementService extends BaseService<MetaElementDTO> {

  List<MetaElementDTO> findMetaElementsByPageId(String pageId);

}
