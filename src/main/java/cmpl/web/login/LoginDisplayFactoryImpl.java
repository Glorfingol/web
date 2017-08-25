package cmpl.web.login;

import cmpl.web.core.factory.BackDisplayFactoryImpl;
import cmpl.web.footer.FooterFactory;
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

  protected LoginDisplayFactoryImpl(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory) {
    super(menuFactory, footerFactory, messageSource, metaElementFactory);
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param menuFactory
   * @param footerFactory
   * @param messageSource
   * @param metaElementFactory
   * @return
   */
  public static LoginDisplayFactoryImpl fromFactoriesAndMessageResource(MenuFactory menuFactory,
      FooterFactory footerFactory, WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory) {
    return new LoginDisplayFactoryImpl(menuFactory, footerFactory, messageSource, metaElementFactory);
  }

}
