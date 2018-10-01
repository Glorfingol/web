package com.cmpl.web.core.style;

import com.cmpl.web.core.common.service.DefaultReadOnlyService;
import com.cmpl.web.core.models.Style;

public class DefaultRenderingStyleService extends
  DefaultReadOnlyService<RenderingStyleDTO, Style> implements RenderingStyleService {


  public DefaultRenderingStyleService(StyleDAO dao, RenderingStyleMapper mapper) {
    super(dao, mapper);
  }
}
