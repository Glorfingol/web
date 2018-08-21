package com.cmpl.web.core.factory;

import com.cmpl.web.core.common.message.WebMessageSource;

/**
 * Implementation de l'nterface commune de factory pour les pages
 *
 * @author Louis
 */
public class BaseDisplayFactoryImpl extends BaseFactoryImpl implements BaseDisplayFactory {

  protected BaseDisplayFactoryImpl(WebMessageSource messageSource) {
    super(messageSource);
  }


}
