package cmpl.web.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.builder.MenuItemBuilder;
import cmpl.web.builder.MetaElementBuilder;
import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.footer.Footer;
import cmpl.web.model.login.LoginFormDisplayBean;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class BackDisplayFactoryImplTest {

  private MenuFactory menuFactory;
  private FooterFactory footerFactory;
  private MetaElementFactory metaElementFactory;
  private WebMessageSourceImpl messageBundle;

  private BackDisplayFactoryImpl displayFactory;

  private Locale locale;

  @Before
  public void setUp() {
    footerFactory = Mockito.mock(FooterFactory.class);
    metaElementFactory = Mockito.mock(MetaElementFactory.class);
    menuFactory = Mockito.mock(MenuFactory.class);
    messageBundle = Mockito.mock(WebMessageSourceImpl.class);
    displayFactory = BackDisplayFactoryImpl.fromFactoriesAndMessageResource(menuFactory, footerFactory, messageBundle,
        metaElementFactory);
    displayFactory = Mockito.spy(displayFactory);
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeMainTitle() throws Exception {

    String title = "title";

    BDDMockito.doReturn(title).when(displayFactory).getI18nValue(Mockito.anyString(), Mockito.eq(locale));

    String result = displayFactory.computeMainTitle(locale);

    Assert.assertEquals(title, result);
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
    footer.setAdresse("an adress");
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

    MetaElement viewport = new MetaElementBuilder().name(viewPortName).content(viewPortContent).toMetaElement();
    MetaElement language = new MetaElementBuilder().name(languageName).content(languageContent).toMetaElement();
    MetaElement title = new MetaElementBuilder().name(titleName).content(titleContent).toMetaElement();
    MetaElement description = new MetaElementBuilder().name(descriptionName).content(descriptionContent)
        .toMetaElement();

    List<MetaElement> metaElements = Lists.newArrayList(viewport, language, title, description);

    BDDMockito.doReturn(metaElements).when(metaElementFactory).computeMetaElementsForBackPage(Mockito.eq(locale));

    List<MetaElement> result = displayFactory.computeMetaElements(locale);

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
    BDDMockito.doReturn(backMenu).when(menuFactory).computeBackMenuItems(Mockito.eq(locale));

    List<MenuItem> result = displayFactory.computeBackMenuItems(locale);
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

    MetaElement viewport = new MetaElementBuilder().name(viewPortName).content(viewPortContent).toMetaElement();
    MetaElement language = new MetaElementBuilder().name(languageName).content(languageContent).toMetaElement();
    MetaElement titleMeta = new MetaElementBuilder().name(titleName).content(titleContent).toMetaElement();
    MetaElement description = new MetaElementBuilder().name(descriptionName).content(descriptionContent)
        .toMetaElement();

    List<MetaElement> metaElements = Lists.newArrayList(viewport, language, titleMeta, description);

    Footer footer = new Footer();
    footer.setAdresse("an adress");
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

    BDDMockito.doReturn(tile).when(displayFactory).computeTileName(Mockito.any(BACK_PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(displayFactory).computeMetaElements(Mockito.eq(locale));
    BDDMockito.doReturn(backMenu).when(displayFactory).computeBackMenuItems(Mockito.eq(locale));
    BDDMockito.doReturn(bean).when(displayFactory).computeLoginFormDisplayBean(Mockito.eq(locale));
    BDDMockito.doReturn(footer).when(displayFactory).computeFooter(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(displayFactory).computeMainTitle(Mockito.eq(locale));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(Mockito.eq(locale));

    ModelAndView result = displayFactory.computeModelAndViewForBackPage(BACK_PAGE.LOGIN, locale);

    Assert.assertEquals(tile, result.getViewName());

    Mockito.verify(displayFactory, Mockito.times(1)).computeTileName(Mockito.any(BACK_PAGE.class), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMetaElements(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeBackMenuItems(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeLoginFormDisplayBean(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeFooter(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMainTitle(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeHiddenLink(Mockito.eq(locale));
  }

  @Test
  public void testComputeHiddenLink() throws Exception {
    String href = "/";
    BDDMockito.doReturn(href).when(displayFactory).getI18nValue(Mockito.eq("back.news.href"), Mockito.eq(locale));

    String result = displayFactory.computeHiddenLink(locale);

    Assert.assertEquals(href, result);

  }

  @Test
  public void testComputeTileName() throws Exception {
    String tile = "login";

    BDDMockito.doReturn(tile).when(displayFactory).getI18nValue(Mockito.anyString(), Mockito.eq(locale));

    String result = displayFactory.computeTileName(BACK_PAGE.LOGIN, locale);
    Assert.assertEquals(tile, result);
  }
}
