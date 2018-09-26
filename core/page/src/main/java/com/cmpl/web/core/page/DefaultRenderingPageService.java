package com.cmpl.web.core.page;

import com.cmpl.web.core.common.service.DefaultReadOnlyService;
import com.cmpl.web.core.models.Page;

public class DefaultRenderingPageService extends
  DefaultReadOnlyService<RenderingPageDTO, Page> implements RenderingPageService {

  public DefaultRenderingPageService(PageDAO dao, RenderingPageMapper mapper) {
    super(dao, mapper);
  }


}
