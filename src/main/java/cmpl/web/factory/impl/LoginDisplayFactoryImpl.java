package cmpl.web.factory.impl;

import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.LoginDisplayFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;

public class LoginDisplayFactoryImpl extends BackDisplayFactoryImpl implements LoginDisplayFactory {

  protected LoginDisplayFactoryImpl(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory) {
    super(menuFactory, footerFactory, messageSource, metaElementFactory);
  }

  public static LoginDisplayFactoryImpl fromFactoriesAndMessageResource(MenuFactory menuFactory,
      FooterFactory footerFactory, WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory) {
    return new LoginDisplayFactoryImpl(menuFactory, footerFactory, messageSource, metaElementFactory);
  }

}
