package com.cmpl.web.core.carousel;

import java.util.List;

import com.cmpl.web.core.common.service.BaseService;

public interface CarouselService extends BaseService<CarouselDTO> {

  List<CarouselDTO> findByPageId(String pageId);
}
