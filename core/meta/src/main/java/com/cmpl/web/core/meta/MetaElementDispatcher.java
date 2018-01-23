package com.cmpl.web.core.meta;

import java.util.Locale;

import com.cmpl.web.core.common.exception.BaseException;

public interface MetaElementDispatcher {

  MetaElementResponse createEntity(String pageId, MetaElementCreateForm form, Locale locale);

  MetaElementResponse createEntity(String pageId, OpenGraphMetaElementCreateForm form,
      Locale locale);

  void deleteMetaEntity(String metaId, Locale locale) throws BaseException;

  void deleteOpenGraphMetaEntity(String openGraphMetaId, Locale locale) throws BaseException;
}
