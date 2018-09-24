package com.cmpl.web.core.page;

import com.cmpl.web.core.common.service.BaseService;

import java.util.List;

/**
 * Interface des pages
 *
 * @author Louis
 */
public interface PageService extends BaseService<PageDTO> {


    List<PageDTO> getPages();

    PageDTO updateEntity(PageDTO dto, String localeCode);

    PageDTO createEntity(PageDTO dto, String localeCode);

    PageDTO getEntity(Long pageId, String localeCode);

}
