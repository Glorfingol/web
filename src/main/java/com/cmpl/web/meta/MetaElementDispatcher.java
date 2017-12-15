package com.cmpl.web.meta;

import java.util.Locale;

import com.cmpl.web.core.model.BaseException;

public interface MetaElementDispatcher {

  MetaElementResponse createEntity(String pageId, MetaElementCreateForm form, Locale locale);

  MetaElementResponse createEntity(String pageId, OpenGraphMetaElementCreateForm form, Locale locale);

  void deleteMetaEntity(String metaId, Locale locale) throws BaseException;

  void deleteOpenGraphMetaEntity(String openGraphMetaId, Locale locale) throws BaseException;
}
