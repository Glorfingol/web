package com.cmpl.web.core.meta;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;

@Repository
public interface OpenGraphMetaElementRepository extends BaseRepository<OpenGraphMetaElement> {

  List<OpenGraphMetaElement> findByPageId(String pageId);

}
