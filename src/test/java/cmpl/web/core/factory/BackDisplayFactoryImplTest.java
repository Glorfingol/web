package cmpl.web.core.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.builder.MenuItemBuilder;
import cmpl.web.builder.MetaElementBuilder;
import cmpl.web.footer.Footer;
import cmpl.web.footer.FooterFactory;
import cmpl.web.login.LoginFormDisplayBean;
import cmpl.web.menu.MenuFactory;
import cmpl.web.menu.MenuItem;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.meta.MetaElementToDelete;
import cmpl.web.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class BackDisplayFactoryImplTest {

  @Mock
  private MenuFactory menuFactory;
  @Mock
  private FooterFactory footerFactory;
  @Mock
  private MetaElementFactory metaElementFactory;
  @Mock
  private WebMessageSourceImpl messageSource;

  @InjectMocks
  @Spy
  private BackDisplayFactoryImpl displayFactory;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeLoginFormDisplayBean() throws Exception {

    String name = "name";
    String password = "password";
    String logout = "logout";
    String error = "error";

    BDDMockito.doReturn(name).when(displayFactory).getI18nValue(Mockito.eq("user.name"), Mockito.eq(locale));
    BDDMockito.doReturn(password).when(displayFactory).getI18nValue(Mockito.eq("user.password"), Mockito.eq(locale));
    BDDMockito.doReturn(logout).when(displayFactory).getI18nValue(Mockito.eq("user.logout"), Mockito.eq(locale));
    BDDMockito.doReturn(error).when(displayFactory).getI18nValue(Mockito.eq("user.error"), Mockito.eq(locale));

    LoginFormDisplayBean result = displayFactory.computeLoginFormDisplayBean(locale);
    Assert.assertEquals(name, result.getUserLabel());
    Assert.assertEquals(password, result.getPasswordLabel());
    Assert.assertEquals(logout, result.getTimeoutLabel());
    Assert.assertEquals(error, result.getErrorLabel());
  }

  @Test
  public void testComputeFooter() throws Exception {

    Footer footer = new Footer();
    footer.setRue("an adress");
    footer.setLibelle("a label");
    footer.setTelephone("0100000000");

    BDDMockito.doReturn(footer).when(footerFactory).computeFooter(Mockito.eq(locale));

    Footer result = displayFactory.computeFooter(locale);

    Assert.assertEquals(footer, result);
  }

  @Test
  public void testComputeMetaElements() throws Exception {

    String viewPortName = "viewport";
    String viewPortContent = "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no";

    String languageName = "viewport";
    String languageContent = locale.getLanguage();

    String titleName = "title";
    String titleContent = "title";

    String descriptionName = "description";
    String descriptionContent = "description";

    MetaElementToDelete viewport = new MetaElementBuilder().name(viewPortName).content(viewPortContent).toMetaElement();
    MetaElementToDelete language = new MetaElementBuilder().name(languageName).content(languageContent).toMetaElement();
    MetaElementToDelete title = new MetaElementBuilder().name(titleName).content(titleContent).toMetaElement();
    MetaElementToDelete description = new MetaElementBuilder().name(descriptionName).content(descriptionContent)
        .toMetaElement();

    List<MetaElementToDelete> metaElements = Lists.newArrayList(viewport, language, title, description);

    BDDMockito.doReturn(metaElements).when(metaElementFactory).computeMetaElementsForBackPage(Mockito.eq(locale));

    List<MetaElementToDelete> result = displayFactory.computeMetaElements(locale);

    Assert.assertEquals(metaElements, result);

  }

  @Test
  public void testComputeBackMenuItems() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();
    MenuItem news = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();

    List<MenuItem> backMenu = Lists.newArrayList(index, news);
    BDDMockito.doReturn(backMenu).when(menuFactory)
        .computeBackMenuItems(Mockito.any(BACK_PAGE.class), Mockito.eq(locale));

    List<MenuItem> result = displayFactory.computeBackMenuItems(BACK_PAGE.LOGIN, locale);
    Assert.assertEquals(backMenu, result);
  }

  @Test
  public void testComputeModelAndViewForBackPage() throws Exception {

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();
    MenuItem news = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();

    List<MenuItem> backMenu = Lists.newArrayList(index, news);

    String viewPortName = "viewport";
    String viewPortContent = "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no";

    String languageName = "viewport";
    String languageContent = locale.getLanguage();

    String titleName = "title";
    String titleContent = "title";

    String descriptionName = "description";
    String descriptionContent = "description";

    MetaElementToDelete viewport = new MetaElementBuilder().name(viewPortName).content(viewPortContent).toMetaElement();
    MetaElementToDelete language = new MetaElementBuilder().name(languageName).content(languageContent).toMetaElement();
    MetaElementToDelete titleMeta = new MetaElementBuilder().name(titleName).content(titleContent).toMetaElement();
    MetaElementToDelete description = new MetaElementBuilder().name(descriptionName).content(descriptionContent)
        .toMetaElement();

    List<MetaElementToDelete> metaElements = Lists.newArrayList(viewport, language, titleMeta, description);

    Footer footer = new Footer();
    footer.setRue("an adress");
    footer.setLibelle("a label");
    footer.setTelephone("0100000000");

    String name = "name";
    String password = "password";
    String logout = "logout";
    String error = "error";

    LoginFormDisplayBean bean = new LoginFormDisplayBean();
    bean.setUserLabel(name);
    bean.setErrorLabel(error);
    bean.setTimeoutLabel(logout);
    bean.setPasswordLabel(password);

    BDDMockito.doReturn(tile).when(displayFactory).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(displayFactory).computeMetaElements(Mockito.eq(locale));
    BDDMockito.doReturn(backMenu).when(displayFactory)
        .computeBackMenuItems(Mockito.any(BACK_PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(bean).when(displayFactory).computeLoginFormDisplayBean(Mockito.eq(locale));
    BDDMockito.doReturn(footer).when(displayFactory).computeFooter(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(displayFactory).computeMainTitle(Mockito.eq(locale));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(Mockito.eq(locale));

    ModelAndView result = displayFactory.computeModelAndViewForBackPage(BACK_PAGE.LOGIN, locale);

    Assert.assertEquals(tile, result.getViewName());

    Assert.assertEquals(metaElements, result.getModel().get("metaItems"));
    Assert.assertEquals(backMenu, result.getModel().get("menuItems"));
    Assert.assertEquals(bean, result.getModel().get("loginForm"));
    Assert.assertEquals(footer, result.getModel().get("footer"));
    Assert.assertEquals(title, result.getModel().get("mainTitle"));
    Assert.assertEquals(href, result.getModel().get("hiddenLink"));

    Mockito.verify(displayFactory, Mockito.times(1)).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMetaElements(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeBackMenuItems(Mockito.any(BACK_PAGE.class),
        Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeLoginFormDisplayBean(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeFooter(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMainTitle(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeHiddenLink(Mockito.eq(locale));
  }

}
