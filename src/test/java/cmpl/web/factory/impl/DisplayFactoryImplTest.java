package cmpl.web.factory.impl;

import java.io.File;
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

import cmpl.web.builder.MenuItemBuilder;
import cmpl.web.builder.MetaElementBuilder;
import cmpl.web.factory.CarouselFactory;
import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.message.WebMessageSource;
import cmpl.web.model.footer.Footer;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.page.PAGE;

@RunWith(MockitoJUnitRunner.class)
public class DisplayFactoryImplTest {

  @Mock
  private MenuFactory menuFactory;
  @Mock
  private FooterFactory footerFactory;
  @Mock
  private MetaElementFactory metaElementFactory;
  @Mock
  private WebMessageSource messageSource;
  @Mock
  private CarouselFactory carouseFactory;

  @InjectMocks
  @Spy
  private DisplayFactoryImpl displayFactory;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
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

    List<MetaElement> metaElements = Lists.newArrayList(title, description, language, viewport);

    BDDMockito.doReturn(metaElements).when(metaElementFactory)
        .computeMetaElementsForPage(Mockito.eq(locale), Mockito.eq(PAGE.INDEX));

    List<MetaElement> result = displayFactory.computeMetaElements(locale, PAGE.INDEX);

    Assert.assertEquals(metaElements, result);
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
  public void testComputeMenuItems() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();
    MenuItem technics = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems)
        .toMenuItem();

    List<MenuItem> backMenu = Lists.newArrayList(index, technics);
    BDDMockito.doReturn(backMenu).when(menuFactory).computeMenuItems(Mockito.any(PAGE.class), Mockito.eq(locale));

    List<MenuItem> result = displayFactory.computeMenuItems(PAGE.INDEX, locale);
    Assert.assertEquals(backMenu, result);
  }

  @Test
  public void testComputeModelAndViewForPage_Index() throws Exception {
    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();
    MenuItem news = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();

    List<MenuItem> menu = Lists.newArrayList(index, news);

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

    BDDMockito.doReturn(tile).when(displayFactory).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(displayFactory)
        .computeMetaElements(Mockito.eq(locale), Mockito.any(PAGE.class));
    BDDMockito.doReturn(menu).when(displayFactory).computeMenuItems(Mockito.any(PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(footer).when(displayFactory).computeFooter(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(displayFactory).computeMainTitle(Mockito.eq(locale));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(displayFactory).computeCarouselImagesFiles(Mockito.eq(locale));

    ModelAndView result = displayFactory.computeModelAndViewForPage(PAGE.INDEX, locale);

    Assert.assertEquals(tile, result.getViewName());

    Assert.assertEquals(metaElements, result.getModel().get("metaItems"));
    Assert.assertEquals(menu, result.getModel().get("menuItems"));
    Assert.assertEquals(footer, result.getModel().get("footer"));
    Assert.assertEquals(title, result.getModel().get("mainTitle"));
    Assert.assertEquals(href, result.getModel().get("hiddenLink"));

    Mockito.verify(displayFactory, Mockito.times(1)).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMetaElements(Mockito.eq(locale), Mockito.any(PAGE.class));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMenuItems(Mockito.any(PAGE.class), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeFooter(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMainTitle(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeHiddenLink(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeCarouselImagesFiles(Mockito.eq(locale));
  }

  @Test
  public void testComputeModelAndViewForPage_Not_Index() throws Exception {
    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();
    MenuItem news = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();

    List<MenuItem> menu = Lists.newArrayList(index, news);

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

    BDDMockito.doReturn(tile).when(displayFactory).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(displayFactory)
        .computeMetaElements(Mockito.eq(locale), Mockito.any(PAGE.class));
    BDDMockito.doReturn(menu).when(displayFactory).computeMenuItems(Mockito.any(PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(footer).when(displayFactory).computeFooter(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(displayFactory).computeMainTitle(Mockito.eq(locale));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(Mockito.eq(locale));

    ModelAndView result = displayFactory.computeModelAndViewForPage(PAGE.NEWS, locale);

    Assert.assertEquals(tile, result.getViewName());

    Assert.assertEquals(metaElements, result.getModel().get("metaItems"));
    Assert.assertEquals(menu, result.getModel().get("menuItems"));
    Assert.assertEquals(footer, result.getModel().get("footer"));
    Assert.assertEquals(title, result.getModel().get("mainTitle"));
    Assert.assertEquals(href, result.getModel().get("hiddenLink"));

    Mockito.verify(displayFactory, Mockito.times(1)).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMetaElements(Mockito.eq(locale), Mockito.any(PAGE.class));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMenuItems(Mockito.any(PAGE.class), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeFooter(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMainTitle(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeHiddenLink(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(0)).computeCarouselImagesFiles(Mockito.eq(locale));
  }

  @Test
  public void testComputeCarouselImagesFiles() throws Exception {
    String srcs = "static/img/logo/logo.jpg;static/img/logo/logoSmall.jpg";

    BDDMockito.doReturn(srcs).when(displayFactory).getI18nValue(Mockito.anyString(), Mockito.eq(locale));
    List<File> result = displayFactory.computeCarouselImagesFiles(locale);

    Assert.assertFalse(CollectionUtils.isEmpty(result));

    for (File file : result) {
      Assert.assertTrue(file.exists());
    }
  }

}
