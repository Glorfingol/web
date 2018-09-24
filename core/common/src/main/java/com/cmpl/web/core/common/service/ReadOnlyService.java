package com.cmpl.web.core.common.service;

import com.cmpl.web.core.common.dto.BaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ReadOnlyService<DTO extends BaseDTO> {

    /**
     * Recuperer une entite
     */
    DTO getEntity(Long id);

    /**
     * Recuperer toutes les entites
     */
    List<DTO> getEntities();

    /**
     * Recuperer une page d'entites
     */
    Page<DTO> getPagedEntities(PageRequest pageRequest);

    /**
     * Faire une recherche
     */
    Page<DTO> searchEntities(PageRequest pageRequest, String query);
}
