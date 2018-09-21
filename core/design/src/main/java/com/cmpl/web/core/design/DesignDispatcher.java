package com.cmpl.web.core.design;

import com.cmpl.web.core.common.exception.BaseException;
import java.util.Locale;

public interface DesignDispatcher {

  DesignResponse createEntity(DesignCreateForm createForm, Locale locale) throws BaseException;

  void deleteEntity(Long websiteId, Long styleId) throws BaseException;

}
