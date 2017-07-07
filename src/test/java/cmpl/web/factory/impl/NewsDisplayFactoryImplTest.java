package cmpl.web.factory.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import cmpl.web.builder.NewsEntryDTOBuilder;
import cmpl.web.factory.CarouselFactory;
import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.context.ContextHolder;
import cmpl.web.model.footer.Footer;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.news.display.NewsEntryDisplayBean;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.page.PAGE;
import cmpl.web.service.NewsEntryService;

@RunWith(MockitoJUnitRunner.class)
public class NewsDisplayFactoryImplTest {

  @Mock
  private MenuFactory menuFactory;
  @Mock
  private FooterFactory footerFactory;
  @Mock
  private MetaElementFactory metaElementFactory;
  @Mock
  private WebMessageSourceImpl messageSource;
  @Mock
  private NewsEntryService newsEntryService;
  @Mock
  private CarouselFactory carouselFactory;
  @Mock
  private ContextHolder contextHolder;

  @InjectMocks
  @Spy
  private NewsDisplayFactoryImpl displayFactory;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeNewsEntryDisplayBean() throws Exception {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    String imageDisplaySrc = "http://cm-pl.com";

    String autor = "author";
    Date date = new Date();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().author(autor).creationDate(date).id(1L).toNewsEntryDTO();

    String labelPar = "par";
    String labelLe = "le";
    String labelAccroche = "accroche";

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(newsEntry, imageDisplaySrc, labelPar, labelLe,
        dateFormat, labelAccroche);

    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(Mockito.any(Long.class));
    BDDMockito.doReturn(displayBean).when(displayFactory)
        .computeNewsEntryDisplayBean(Mockito.eq(locale), Mockito.eq(newsEntry));

    NewsEntryDisplayBean result = displayFactory.computeNewsEntry(locale, "123");

    Assert.assertEquals(displayBean.getNewsEntryId(), result.getNewsEntryId());
    Assert.assertEquals(displayBean.getPanelHeading(), result.getPanelHeading());

  }

  @Test
  public void testComputeNewsEntryDisplayBeanLocaleNewsEntryDTO() throws Exception {

    SimpleDateFormat formatted = new SimpleDateFormat("dd/MM/yy");

    String autor = "author";
    Date date = new Date();
    String formattedDate = formatted.format(date);
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().author(autor).creationDate(date).id(1L).toNewsEntryDTO();

    String labelPar = "par";
    String labelLe = "le";
    String labelAccroche = "accroche";

    BDDMockito.doReturn(new SimpleDateFormat("dd/MM/yy")).when(contextHolder).getDateFormat();
    BDDMockito.doReturn(labelPar).when(displayFactory).getI18nValue(Mockito.eq("news.entry.by"), Mockito.eq(locale));
    BDDMockito.doReturn(labelLe).when(displayFactory).getI18nValue(Mockito.eq("news.entry.the"), Mockito.eq(locale));
    BDDMockito.doReturn(labelAccroche).when(displayFactory)
        .getI18nValue(Mockito.eq("news.entry.call"), Mockito.eq(locale));

    NewsEntryDisplayBean result = displayFactory.computeNewsEntryDisplayBean(locale, newsEntry);

    Assert.assertTrue(newsEntry.getId() == Long.valueOf(result.getNewsEntryId()));
    Assert.assertEquals(labelPar + " " + autor + " " + labelLe + " " + formattedDate, result.getPanelHeading());

  }

  @Test
  public void testComputeModelAndViewForNewsEntry() throws Exception {
    String tile = "actualites";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorFront = "decorator_front";
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
    footer.setRue("an adress");
    footer.setLibelle("a label");
    footer.setTelephone("0100000000");

    BDDMockito.doReturn(decoratorFront).when(displayFactory).computeDecoratorFrontTileName(Mockito.eq(locale));
    BDDMockito.doReturn(tile).when(displayFactory).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(displayFactory)
        .computeMetaElements(Mockito.eq(locale), Mockito.any(PAGE.class));
    BDDMockito.doReturn(menu).when(displayFactory).computeMenuItems(Mockito.any(PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(footer).when(displayFactory).computeFooter(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(displayFactory).computeMainTitle(Mockito.eq(locale));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(Mockito.eq(locale));

    ModelAndView result = displayFactory.computeModelAndViewForNewsEntry(locale, "123");

    Assert.assertEquals(decoratorFront, result.getViewName());
    Assert.assertEquals(tile, result.getModel().get("content"));

    Mockito.verify(displayFactory, Mockito.times(1)).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMetaElements(Mockito.eq(locale), Mockito.any(PAGE.class));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMenuItems(Mockito.any(PAGE.class), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeFooter(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMainTitle(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeHiddenLink(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeNewsEntry(Mockito.eq(locale), Mockito.anyString());
  }

  @Test
  public void testComputeModelAndViewForPage_Not_Empty() throws Exception {
    String tile = "actualites";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorFront = "decorator_front";
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
    footer.setRue("an adress");
    footer.setLibelle("a label");
    footer.setTelephone("0100000000");

    BDDMockito.doReturn(decoratorFront).when(displayFactory).computeDecoratorFrontTileName(Mockito.eq(locale));
    BDDMockito.doReturn(tile).when(displayFactory).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(displayFactory)
        .computeMetaElements(Mockito.eq(locale), Mockito.any(PAGE.class));
    BDDMockito.doReturn(menu).when(displayFactory).computeMenuItems(Mockito.any(PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(footer).when(displayFactory).computeFooter(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(displayFactory).computeMainTitle(Mockito.eq(locale));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(Mockito.eq(locale));

    ModelAndView result = displayFactory.computeModelAndViewForPage(PAGE.NEWS, locale);

    Assert.assertEquals(decoratorFront, result.getViewName());
    Assert.assertEquals(tile, result.getModel().get("content"));

    Mockito.verify(displayFactory, Mockito.times(1)).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMetaElements(Mockito.eq(locale), Mockito.any(PAGE.class));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMenuItems(Mockito.any(PAGE.class), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeFooter(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMainTitle(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeHiddenLink(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeNewsEntries(Mockito.eq(locale));
  }

  @Test
  public void testComputeModelAndViewForPage_Empty() throws Exception {
    String tile = "actualites";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorFront = "decorator_front";
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
    footer.setRue("an adress");
    footer.setLibelle("a label");
    footer.setTelephone("0100000000");

    BDDMockito.doReturn(decoratorFront).when(displayFactory).computeDecoratorFrontTileName(Mockito.eq(locale));
    BDDMockito.doReturn(tile).when(displayFactory).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(displayFactory)
        .computeMetaElements(Mockito.eq(locale), Mockito.any(PAGE.class));
    BDDMockito.doReturn(menu).when(displayFactory).computeMenuItems(Mockito.any(PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(footer).when(displayFactory).computeFooter(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(displayFactory).computeMainTitle(Mockito.eq(locale));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(displayFactory).computeCarouselImagesFiles(Mockito.eq(locale));

    ModelAndView result = displayFactory.computeModelAndViewForPage(PAGE.INDEX, locale);

    Assert.assertEquals(decoratorFront, result.getViewName());
    Assert.assertEquals(tile, result.getModel().get("content"));

    Mockito.verify(displayFactory, Mockito.times(1)).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMetaElements(Mockito.eq(locale), Mockito.any(PAGE.class));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMenuItems(Mockito.any(PAGE.class), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeFooter(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMainTitle(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeHiddenLink(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(0)).computeNewsEntries(Mockito.eq(locale));
  }

  @Test
  public void testComputeNewsEntries() throws Exception {

    SimpleDateFormat formatted = new SimpleDateFormat("dd/MM/yy");

    String autor = "author";
    Date date = new Date();
    String formattedDate = formatted.format(date);

    String labelPar = "par";
    String labelLe = "le";

    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().author(autor).creationDate(date).id(1L).toNewsEntryDTO();
    List<NewsEntryDTO> newsEntries = Lists.newArrayList(newsEntry);

    BDDMockito.doReturn(new SimpleDateFormat("dd/MM/yy")).when(contextHolder).getDateFormat();
    BDDMockito.doReturn(newsEntries).when(newsEntryService).getEntities();
    BDDMockito.doReturn(labelPar).when(displayFactory).getI18nValue(Mockito.eq("news.entry.by"), Mockito.eq(locale));
    BDDMockito.doReturn(labelLe).when(displayFactory).getI18nValue(Mockito.eq("news.entry.the"), Mockito.eq(locale));

    List<NewsEntryDisplayBean> result = displayFactory.computeNewsEntries(locale);

    Assert.assertFalse(CollectionUtils.isEmpty(result));
    Assert.assertTrue(newsEntry.getId() == Long.valueOf(result.get(0).getNewsEntryId()));
    Assert.assertEquals(labelPar + " " + autor + " " + labelLe + " " + formattedDate, result.get(0).getPanelHeading());

  }

  @Test
  public void testComputeNewsEntries_Empty() throws Exception {

    BDDMockito.doReturn(Lists.newArrayList()).when(newsEntryService).getEntities();

    List<NewsEntryDisplayBean> result = displayFactory.computeNewsEntries(locale);

    Assert.assertTrue(CollectionUtils.isEmpty(result));

  }
}
