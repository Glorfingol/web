package com.cmpl.web.core.meta;

import java.util.List;

import com.cmpl.web.core.common.service.BaseService;

public interface OpenGraphMetaElementService extends BaseService<OpenGraphMetaElementDTO> {

  List<OpenGraphMetaElementDTO> findOpenGraphMetaElementsByPageId(String pageId);

}
