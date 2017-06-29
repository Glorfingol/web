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
import cmpl.web.builder.NewsContentDTOBuilder;
import cmpl.web.builder.NewsEntryDTOBuilder;
import cmpl.web.builder.NewsEntryRequestBuilder;
import cmpl.web.builder.NewsFormDisplayBeanBuilder;
import cmpl.web.builder.NewsImageDTOBuilder;
import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.footer.Footer;
import cmpl.web.model.login.LoginFormDisplayBean;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.news.display.NewsEntryDisplayBean;
import cmpl.web.model.news.display.NewsFormDisplayBean;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.page.BACK_PAGE;
import cmpl.web.service.NewsEntryService;

@RunWith(MockitoJUnitRunner.class)
public class NewsManagerDisplayFactoryImplTest {

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

  @InjectMocks
  @Spy
  private NewsManagerDisplayFactoryImpl displayFactory;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeForm() throws Exception {
    String titleLabel = "title";
    String titleHelp = "titleHelp";

    String authorLabel = "label";
    String authorHelp = "labelHelp";

    String tagsLabel = "tags";
    String tagsHelp = "tagsHelp";

    String contentLabel = "content";
    String contentHelp = "contentHelp";

    String imageLabel = "image";
    String imageHelp = "imageHelp";

    String legendLabel = "legend";
    String legendHelp = "legendHelp";

    String altLabel = "alt";
    String altHelp = "altHelp";

    BDDMockito.doReturn(titleLabel).when(displayFactory).getI18nValue(Mockito.eq("title.label"), Mockito.eq(locale));
    BDDMockito.doReturn(titleHelp).when(displayFactory).getI18nValue(Mockito.eq("title.help"), Mockito.eq(locale));

    BDDMockito.doReturn(authorLabel).when(displayFactory).getI18nValue(Mockito.eq("author.label"), Mockito.eq(locale));
    BDDMockito.doReturn(authorHelp).when(displayFactory).getI18nValue(Mockito.eq("author.help"), Mockito.eq(locale));

    BDDMockito.doReturn(tagsLabel).when(displayFactory).getI18nValue(Mockito.eq("tags.label"), Mockito.eq(locale));
    BDDMockito.doReturn(tagsHelp).when(displayFactory).getI18nValue(Mockito.eq("tags.help"), Mockito.eq(locale));

    BDDMockito.doReturn(contentLabel).when(displayFactory)
        .getI18nValue(Mockito.eq("content.label"), Mockito.eq(locale));
    BDDMockito.doReturn(contentHelp).when(displayFactory).getI18nValue(Mockito.eq("content.help"), Mockito.eq(locale));

    BDDMockito.doReturn(imageLabel).when(displayFactory).getI18nValue(Mockito.eq("image.label"), Mockito.eq(locale));
    BDDMockito.doReturn(imageHelp).when(displayFactory).getI18nValue(Mockito.eq("image.help"), Mockito.eq(locale));

    BDDMockito.doReturn(legendLabel).when(displayFactory).getI18nValue(Mockito.eq("legend.label"), Mockito.eq(locale));
    BDDMockito.doReturn(legendHelp).when(displayFactory).getI18nValue(Mockito.eq("legend.help"), Mockito.eq(locale));

    BDDMockito.doReturn(altLabel).when(displayFactory).getI18nValue(Mockito.eq("alt.label"), Mockito.eq(locale));
    BDDMockito.doReturn(altHelp).when(displayFactory).getI18nValue(Mockito.eq("alt.help"), Mockito.eq(locale));

    NewsFormDisplayBean result = displayFactory.computeForm(locale);

    Assert.assertEquals(titleLabel, result.getTitleLabel());
    Assert.assertEquals(titleHelp, result.getTitleHelp());
    Assert.assertEquals(authorLabel, result.getAuthorLabel());
    Assert.assertEquals(authorHelp, result.getAuthorHelp());
    Assert.assertEquals(tagsLabel, result.getTagsLabel());
    Assert.assertEquals(tagsHelp, result.getTagsHelp());
    Assert.assertEquals(contentLabel, result.getContentLabel());
    Assert.assertEquals(contentHelp, result.getContentHelp());
    Assert.assertEquals(imageLabel, result.getImageLabel());
    Assert.assertEquals(imageHelp, result.getImageHelp());
    Assert.assertEquals(legendLabel, result.getLegendLabel());
    Assert.assertEquals(legendHelp, result.getLegendHelp());
    Assert.assertEquals(altLabel, result.getAltLabel());
    Assert.assertEquals(altHelp, result.getAltHelp());
  }

  @Test
  public void testComputeNewsEditBeanDisplayBean() throws Exception {

    String dateFormat = "dd/MM/yy";

    String autor = "author";
    Date date = new Date();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().author(autor).creationDate(date).id(1L).toNewsEntryDTO();

    String labelPar = "par";
    String labelLe = "le";
    String labelAccroche = "accroche";

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(newsEntry, labelPar, labelLe, dateFormat, labelAccroche);

    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(Mockito.any(Long.class));
    BDDMockito.doReturn(displayBean).when(displayFactory)
        .computeNewsEntryDisplayBean(Mockito.eq(locale), Mockito.eq(newsEntry));

    NewsEntryDisplayBean result = displayFactory.computeNewsEntryDisplayBean(locale, newsEntry);

    Assert.assertEquals(displayBean.getNewsEntryId(), result.getNewsEntryId());
    Assert.assertEquals(displayBean.getPanelHeading(), result.getPanelHeading());

  }

  @Test
  public void testComputeNewsEntryDisplayBeans() throws Exception {

    SimpleDateFormat formatted = new SimpleDateFormat("dd/MM/yy");

    String autor = "author";
    Date date = new Date();
    String formattedDate = formatted.format(date);

    String labelPar = "par";
    String labelLe = "le";

    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().author(autor).creationDate(date).id(1L).toNewsEntryDTO();
    List<NewsEntryDTO> newsEntries = Lists.newArrayList(newsEntry);

    BDDMockito.doReturn(newsEntries).when(newsEntryService).getEntities();
    BDDMockito.doReturn(labelPar).when(displayFactory).getI18nValue(Mockito.eq("news.entry.by"), Mockito.eq(locale));
    BDDMockito.doReturn(labelLe).when(displayFactory).getI18nValue(Mockito.eq("news.entry.the"), Mockito.eq(locale));

    List<NewsEntryDisplayBean> result = displayFactory.computeNewsEntryDisplayBeans(locale);

    Assert.assertFalse(CollectionUtils.isEmpty(result));
    Assert.assertTrue(newsEntry.getId() == Long.valueOf(result.get(0).getNewsEntryId()));
    Assert.assertEquals(labelPar + " " + autor + " " + labelLe + " " + formattedDate, result.get(0).getPanelHeading());

  }

  @Test
  public void testComputeNewsEntryDisplayBeans_Empty() throws Exception {

    BDDMockito.doReturn(Lists.newArrayList()).when(newsEntryService).getEntities();

    List<NewsEntryDisplayBean> result = displayFactory.computeNewsEntryDisplayBeans(locale);

    Assert.assertTrue(CollectionUtils.isEmpty(result));

  }

  @Test
  public void testComputeModelAndViewForOneNewsEntry() throws Exception {

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

    String name = "name";
    String password = "password";
    String logout = "logout";
    String error = "error";

    LoginFormDisplayBean bean = new LoginFormDisplayBean();
    bean.setUserLabel(name);
    bean.setErrorLabel(error);
    bean.setTimeoutLabel(logout);
    bean.setPasswordLabel(password);

    String author = "author";
    Date date = new Date();
    String tags = "tag;lol";
    String content = "content";
    String alt = "alt";

    NewsEntryRequest request = new NewsEntryRequestBuilder().toNewsEntryRequest();

    NewsContentDTO newsContent = new NewsContentDTOBuilder().content(content).id(1L).creationDate(date)
        .modificationDate(date).toNewsContentDTO();

    NewsImageDTO newsImage = new NewsImageDTOBuilder().id(1L).creationDate(date).modificationDate(date).alt(alt)
        .toNewsImageDTO();

    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().author(author).creationDate(date).tags(tags).id(1L).title(title)
        .newsContent(newsContent).newsImage(newsImage).modificationDate(date).toNewsEntryDTO();

    NewsFormDisplayBean form = new NewsFormDisplayBeanBuilder().toNewsFormDisplayBean();

    BDDMockito.doReturn(decoratorBack).when(displayFactory).computeDecoratorBackTileName(Mockito.eq(locale));
    BDDMockito.doReturn(tile).when(displayFactory).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(displayFactory).computeMetaElements(Mockito.eq(locale));
    BDDMockito.doReturn(backMenu).when(displayFactory)
        .computeBackMenuItems(Mockito.any(BACK_PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(bean).when(displayFactory).computeLoginFormDisplayBean(Mockito.eq(locale));
    BDDMockito.doReturn(footer).when(displayFactory).computeFooter(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(displayFactory).computeMainTitle(Mockito.eq(locale));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(Mockito.eq(locale));
    BDDMockito.doReturn(request).when(displayFactory).computeNewsEntryRequest(Mockito.any(NewsEntryDTO.class));
    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(Mockito.anyLong());
    BDDMockito.doReturn(form).when(displayFactory).computeForm(Mockito.eq(locale));

    ModelAndView result = displayFactory.computeModelAndViewForOneNewsEntry(BACK_PAGE.NEWS_UPDATE, locale, "123");

    Assert.assertEquals(decoratorBack, result.getViewName());
    Assert.assertEquals(tile, result.getModel().get("content"));

    Mockito.verify(displayFactory, Mockito.times(1)).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMetaElements(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeBackMenuItems(Mockito.any(BACK_PAGE.class),
        Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeLoginFormDisplayBean(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeFooter(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMainTitle(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeHiddenLink(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeNewsEntryRequest(Mockito.any(NewsEntryDTO.class));
    Mockito.verify(displayFactory, Mockito.times(1)).computeForm(Mockito.eq(locale));

  }

  @Test
  public void testComputeModelAndViewForBackPage() throws Exception {
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

    String name = "name";
    String password = "password";
    String logout = "logout";
    String error = "error";

    LoginFormDisplayBean bean = new LoginFormDisplayBean();
    bean.setUserLabel(name);
    bean.setErrorLabel(error);
    bean.setTimeoutLabel(logout);
    bean.setPasswordLabel(password);

    String author = "author";
    Date date = new Date();
    String tags = "tag;lol";
    String content = "content";
    String alt = "alt";

    NewsContentDTO newsContent = new NewsContentDTOBuilder().content(content).id(1L).creationDate(date)
        .modificationDate(date).toNewsContentDTO();

    NewsImageDTO newsImage = new NewsImageDTOBuilder().id(1L).creationDate(date).modificationDate(date).alt(alt)
        .toNewsImageDTO();

    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().author(author).creationDate(date).tags(tags).id(1L).title(title)
        .newsContent(newsContent).newsImage(newsImage).modificationDate(date).toNewsEntryDTO();

    String dateFormat = "dd/MM/yy";

    String labelPar = "par";
    String labelLe = "le";
    String labelAccroche = "accroche";

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(newsEntry, labelPar, labelLe, dateFormat, labelAccroche);

    NewsFormDisplayBean form = new NewsFormDisplayBeanBuilder().toNewsFormDisplayBean();

    BDDMockito.doReturn(decoratorBack).when(displayFactory).computeDecoratorBackTileName(Mockito.eq(locale));
    BDDMockito.doReturn(tile).when(displayFactory).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(displayFactory).computeMetaElements(Mockito.eq(locale));
    BDDMockito.doReturn(backMenu).when(displayFactory)
        .computeBackMenuItems(Mockito.any(BACK_PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(bean).when(displayFactory).computeLoginFormDisplayBean(Mockito.eq(locale));
    BDDMockito.doReturn(footer).when(displayFactory).computeFooter(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(displayFactory).computeMainTitle(Mockito.eq(locale));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList(displayBean)).when(displayFactory)
        .computeNewsEntryDisplayBeans(Mockito.eq(locale));
    BDDMockito.doReturn(form).when(displayFactory).computeForm(Mockito.eq(locale));

    ModelAndView result = displayFactory.computeModelAndViewForBackPage(BACK_PAGE.NEWS_VIEW, locale);

    Assert.assertEquals(decoratorBack, result.getViewName());
    Assert.assertEquals(tile, result.getModel().get("content"));

    Mockito.verify(displayFactory, Mockito.times(1)).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMetaElements(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeBackMenuItems(Mockito.any(BACK_PAGE.class),
        Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeLoginFormDisplayBean(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeFooter(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMainTitle(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeHiddenLink(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeNewsEntryDisplayBeans(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeForm(Mockito.eq(locale));
  }

}
