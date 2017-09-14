package cmpl.web.facebook;

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
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.builder.ImportablePostBuilder;
import cmpl.web.builder.MenuItemBuilder;
import cmpl.web.builder.MetaElementBuilder;
import cmpl.web.core.model.BaseException;
import cmpl.web.footer.Footer;
import cmpl.web.menu.MenuItem;
import cmpl.web.meta.MetaElementToDelete;
import cmpl.web.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class FacebookDisplayFactoryImplTest {

  @Mock
  private FacebookService facebookService;

  @InjectMocks
  @Spy
  private FacebookDisplayFactoryImpl facebookDisplayFactoryImpl;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testIsAlreadyConnected_True() throws Exception {

    boolean result = facebookDisplayFactoryImpl.isAlreadyConnected();
    Assert.assertTrue(result);
  }

  @Test
  public void testIsAlreadyConnected_False() throws Exception {

    BDDMockito.doThrow(new BaseException()).when(facebookService).getRecentFeed();

    boolean result = facebookDisplayFactoryImpl.isAlreadyConnected();

    Assert.assertFalse(result);
  }

  @Test
  public void testComputeRecentFeeds_Ok() throws Exception {

    ImportablePost post = new ImportablePostBuilder().facebookId("someFacebookId").toImportablePost();
    List<ImportablePost> postsToReturn = Lists.newArrayList(post);

    BDDMockito.doReturn(postsToReturn).when(facebookService).getRecentFeed();

    List<ImportablePost> result = facebookDisplayFactoryImpl.computeRecentFeeds();

    Assert.assertEquals(postsToReturn, result);

  }

  @Test
  public void testComputeRecentFeeds_Exception_Should_Return_Empty_Array() throws Exception {

    BDDMockito.doThrow(new BaseException()).when(facebookService).getRecentFeed();

    List<ImportablePost> result = facebookDisplayFactoryImpl.computeRecentFeeds();

    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testComputeModelAndViewForFacebookAccessPage_Not_Connected() throws Exception {
    ModelAndView model = new ModelAndView("facebook_access");

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorBack = "decorator_back";
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

    BDDMockito.doReturn(decoratorBack).when(facebookDisplayFactoryImpl)
        .computeDecoratorBackTileName(Mockito.eq(locale));
    BDDMockito.doReturn(tile).when(facebookDisplayFactoryImpl).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(facebookDisplayFactoryImpl).computeMetaElements(Mockito.eq(locale));
    BDDMockito.doReturn(backMenu).when(facebookDisplayFactoryImpl)
        .computeBackMenuItems(Mockito.any(BACK_PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(footer).when(facebookDisplayFactoryImpl).computeFooter(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(facebookDisplayFactoryImpl).computeMainTitle(Mockito.eq(locale));
    BDDMockito.doReturn(href).when(facebookDisplayFactoryImpl).computeHiddenLink(Mockito.eq(locale));
    BDDMockito.doReturn(false).when(facebookDisplayFactoryImpl).isAlreadyConnected();
    BDDMockito.doReturn(model).when(facebookDisplayFactoryImpl)
        .computeModelAndViewForBackPage(Mockito.eq(BACK_PAGE.FACEBOOK_ACCESS), Mockito.eq(locale));

    ModelAndView result = facebookDisplayFactoryImpl.computeModelAndViewForFacebookAccessPage(locale);

    Assert.assertEquals("title", result.getModel().get("accessTitle"));
    Assert.assertEquals("access", result.getModel().get("accessInformation"));

    Mockito.verify(facebookDisplayFactoryImpl, Mockito.times(0)).computeModelAndViewForFacebookImportPage(
        Mockito.eq(locale));
    Mockito.verify(facebookDisplayFactoryImpl, Mockito.times(0)).computeRecentFeeds();

  }

  @Test
  public void testComputeModelAndViewForFacebookAccessPage_Connected() throws Exception {

    ModelAndView model = new ModelAndView("facebook_access");

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorBack = "decorator_back";
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

    ImportablePost post = new ImportablePostBuilder().facebookId("someFacebookId").toImportablePost();
    List<ImportablePost> postsToReturn = Lists.newArrayList(post);

    BDDMockito.doReturn(postsToReturn).when(facebookDisplayFactoryImpl).computeRecentFeeds();
    BDDMockito.doReturn(decoratorBack).when(facebookDisplayFactoryImpl)
        .computeDecoratorBackTileName(Mockito.eq(locale));
    BDDMockito.doReturn(tile).when(facebookDisplayFactoryImpl).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(facebookDisplayFactoryImpl).computeMetaElements(Mockito.eq(locale));
    BDDMockito.doReturn(backMenu).when(facebookDisplayFactoryImpl)
        .computeBackMenuItems(Mockito.any(BACK_PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(footer).when(facebookDisplayFactoryImpl).computeFooter(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(facebookDisplayFactoryImpl).computeMainTitle(Mockito.eq(locale));
    BDDMockito.doReturn(href).when(facebookDisplayFactoryImpl).computeHiddenLink(Mockito.eq(locale));
    BDDMockito.doReturn(true).when(facebookDisplayFactoryImpl).isAlreadyConnected();
    BDDMockito.doReturn(model).when(facebookDisplayFactoryImpl)
        .computeModelAndViewForBackPage(Mockito.eq(BACK_PAGE.FACEBOOK_ACCESS), Mockito.eq(locale));

    ModelAndView result = facebookDisplayFactoryImpl.computeModelAndViewForFacebookAccessPage(locale);

    Assert.assertEquals("importAll", result.getModel().get("importAllLabel"));
    Assert.assertEquals("importOne", result.getModel().get("importOneLabel"));
    Assert.assertEquals(postsToReturn, result.getModel().get("feeds"));

    Mockito.verify(facebookDisplayFactoryImpl, Mockito.times(1)).computeRecentFeeds();
    Mockito.verify(facebookDisplayFactoryImpl, Mockito.times(1)).computeModelAndViewForFacebookImportPage(
        Mockito.eq(locale));
  }

  @Test
  public void testComputeModelAndViewForFacebookImportPage_Not_Connected() throws Exception {
    ModelAndView model = new ModelAndView("facebook_access");

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorBack = "decorator_back";
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

    BDDMockito.doReturn(decoratorBack).when(facebookDisplayFactoryImpl)
        .computeDecoratorBackTileName(Mockito.eq(locale));
    BDDMockito.doReturn(tile).when(facebookDisplayFactoryImpl).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(facebookDisplayFactoryImpl).computeMetaElements(Mockito.eq(locale));
    BDDMockito.doReturn(backMenu).when(facebookDisplayFactoryImpl)
        .computeBackMenuItems(Mockito.any(BACK_PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(footer).when(facebookDisplayFactoryImpl).computeFooter(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(facebookDisplayFactoryImpl).computeMainTitle(Mockito.eq(locale));
    BDDMockito.doReturn(href).when(facebookDisplayFactoryImpl).computeHiddenLink(Mockito.eq(locale));
    BDDMockito.doReturn(false).when(facebookDisplayFactoryImpl).isAlreadyConnected();
    BDDMockito.doReturn(model).when(facebookDisplayFactoryImpl)
        .computeModelAndViewForBackPage(Mockito.eq(BACK_PAGE.FACEBOOK_ACCESS), Mockito.eq(locale));

    ModelAndView result = facebookDisplayFactoryImpl.computeModelAndViewForFacebookImportPage(locale);

    Assert.assertEquals("title", result.getModel().get("accessTitle"));
    Assert.assertEquals("access", result.getModel().get("accessInformation"));

    Mockito.verify(facebookDisplayFactoryImpl, Mockito.times(1)).computeModelAndViewForFacebookAccessPage(
        Mockito.eq(locale));
    Mockito.verify(facebookDisplayFactoryImpl, Mockito.times(0)).computeRecentFeeds();
  }

  @Test
  public void testComputeModelAndViewForFacebookImportPage_Connected() throws Exception {

    ModelAndView model = new ModelAndView("facebook_access");

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorBack = "decorator_back";
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

    ImportablePost post = new ImportablePostBuilder().facebookId("someFacebookId").toImportablePost();
    List<ImportablePost> postsToReturn = Lists.newArrayList(post);

    BDDMockito.doReturn(postsToReturn).when(facebookDisplayFactoryImpl).computeRecentFeeds();
    BDDMockito.doReturn(decoratorBack).when(facebookDisplayFactoryImpl)
        .computeDecoratorBackTileName(Mockito.eq(locale));
    BDDMockito.doReturn(tile).when(facebookDisplayFactoryImpl).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(facebookDisplayFactoryImpl).computeMetaElements(Mockito.eq(locale));
    BDDMockito.doReturn(backMenu).when(facebookDisplayFactoryImpl)
        .computeBackMenuItems(Mockito.any(BACK_PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(footer).when(facebookDisplayFactoryImpl).computeFooter(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(facebookDisplayFactoryImpl).computeMainTitle(Mockito.eq(locale));
    BDDMockito.doReturn(href).when(facebookDisplayFactoryImpl).computeHiddenLink(Mockito.eq(locale));
    BDDMockito.doReturn(true).when(facebookDisplayFactoryImpl).isAlreadyConnected();
    BDDMockito.doReturn(model).when(facebookDisplayFactoryImpl)
        .computeModelAndViewForBackPage(Mockito.eq(BACK_PAGE.FACEBOOK_ACCESS), Mockito.eq(locale));

    ModelAndView result = facebookDisplayFactoryImpl.computeModelAndViewForFacebookImportPage(locale);

    Assert.assertEquals("importAll", result.getModel().get("importAllLabel"));
    Assert.assertEquals("importOne", result.getModel().get("importOneLabel"));
    Assert.assertEquals(postsToReturn, result.getModel().get("feeds"));

    Mockito.verify(facebookDisplayFactoryImpl, Mockito.times(1)).computeRecentFeeds();
    Mockito.verify(facebookDisplayFactoryImpl, Mockito.times(0)).computeModelAndViewForFacebookAccessPage(
        Mockito.eq(locale));
  }
}
