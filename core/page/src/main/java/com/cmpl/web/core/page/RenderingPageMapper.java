package com.cmpl.web.core.page;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.Page;

public class RenderingPageMapper extends BaseMapper<RenderingPageDTO, Page> {

    @Override
    public RenderingPageDTO toDTO(Page entity) {
        if (entity == null) {
            return null;
        }
        RenderingPageDTO dto = RenderingPageDTOBuilder.create().build();
        fillObject(entity, dto);
        return dto;
    }

    @Override
    public Page toEntity(RenderingPageDTO dto) {
        Page entity = PageBuilder.create().build();
        fillObject(dto, entity);
        return entity;
    }
}
