package com.cmpl.web.login;

import com.cmpl.web.core.factory.BackDisplayFactoryImpl;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.meta.MetaElementFactory;

/**
 * Implementation de l'interface pour la factory de la page de login
 * 
 * @author Louis
 *
 */
public class LoginDisplayFactoryImpl extends BackDisplayFactoryImpl implements LoginDisplayFactory {

  public LoginDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      MetaElementFactory metaElementFactory) {
    super(menuFactory, messageSource, metaElementFactory);
  }

}
