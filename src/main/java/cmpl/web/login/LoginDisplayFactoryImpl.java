package cmpl.web.login;

import cmpl.web.core.factory.BackDisplayFactoryImpl;
import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;

/**
 * Implementation de l'interface pour la factory de la page de login
 * 
 * @author Louis
 *
 */
public class LoginDisplayFactoryImpl extends BackDisplayFactoryImpl implements LoginDisplayFactory {

  public LoginDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSourceImpl messageSource,
      MetaElementFactory metaElementFactory) {
    super(menuFactory, messageSource, metaElementFactory);
  }

}
