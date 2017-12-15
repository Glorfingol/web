package com.cmpl.web.news;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.builder.MenuItemBuilder;
import com.cmpl.web.builder.MetaElementBuilder;
import com.cmpl.web.builder.NewsContentDTOBuilder;
import com.cmpl.web.builder.NewsContentRequestBuilder;
import com.cmpl.web.builder.NewsEntryDTOBuilder;
import com.cmpl.web.builder.NewsEntryRequestBuilder;
import com.cmpl.web.builder.NewsImageDTOBuilder;
import com.cmpl.web.builder.NewsImageRequestBuilder;
import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.model.PageWrapper;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.menu.MenuItem;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.meta.MetaElementFactory;
import com.cmpl.web.meta.MetaElementToDelete;
import com.cmpl.web.news.NewsContentDTO;
import com.cmpl.web.news.NewsContentRequest;
import com.cmpl.web.news.NewsEntryDTO;
import com.cmpl.web.news.NewsEntryDisplayBean;
import com.cmpl.web.news.NewsEntryRequest;
import com.cmpl.web.news.NewsEntryService;
import com.cmpl.web.news.NewsImageDTO;
import com.cmpl.web.news.NewsImageRequest;
import com.cmpl.web.news.NewsManagerDisplayFactoryImpl;
import com.cmpl.web.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class NewsManagerDisplayFactoryImplTest {

  @Mock
  private MenuFactory menuFactory;
  @Mock
  private MetaElementFactory metaElementFactory;
  @Mock
  private WebMessageSourceImpl messageSource;
  @Mock
  private NewsEntryService newsEntryService;
  @Mock
  private ContextHolder contextHolder;

  @InjectMocks
  @Spy
  private NewsManagerDisplayFactoryImpl displayFactory;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeNewsEditBeanDisplayBean() throws Exception {

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy");
    String imageDisplaySrc = "http://cm-pl.com";

    String autor = "author";
    LocalDate date = LocalDate.now();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().author(autor).creationDate(date).id(1L).toNewsEntryDTO();

    String labelPar = "par";
    String labelLe = "le";
    String labelAccroche = "accroche";
    String labelShowHref = "/pages/actualites/666";

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(newsEntry, imageDisplaySrc, labelPar, labelLe,
        dateFormat, labelAccroche, labelShowHref);

    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(Mockito.any(Long.class));
    BDDMockito.doReturn(displayBean).when(displayFactory)
        .computeNewsEntryDisplayBean(Mockito.eq(locale), Mockito.eq(newsEntry));

    NewsEntryDisplayBean result = displayFactory.computeNewsEntryDisplayBean(locale, newsEntry);

    Assert.assertEquals(displayBean.getNewsEntryId(), result.getNewsEntryId());
    Assert.assertEquals(displayBean.getPanelHeading(), result.getPanelHeading());

  }

  @Test
  public void testComputeNewsEntryDisplayBeans() throws Exception {

    DateTimeFormatter formatted = DateTimeFormatter.ofPattern("dd/MM/yy");

    String autor = "author";
    LocalDate date = LocalDate.now();
    String formattedDate = formatted.format(date);

    String labelPar = "par";
    String labelLe = "le";

    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().author(autor).creationDate(date).id(1L).toNewsEntryDTO();
    List<NewsEntryDTO> newsEntries = Lists.newArrayList(newsEntry);

    BDDMockito.doReturn(formatted).when(contextHolder).getDateFormat();
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

    MetaElementToDelete viewport = new MetaElementBuilder().name(viewPortName).content(viewPortContent).toMetaElement();
    MetaElementToDelete language = new MetaElementBuilder().name(languageName).content(languageContent).toMetaElement();
    MetaElementToDelete titleMeta = new MetaElementBuilder().name(titleName).content(titleContent).toMetaElement();
    MetaElementToDelete description = new MetaElementBuilder().name(descriptionName).content(descriptionContent)
        .toMetaElement();

    List<MetaElementToDelete> metaElements = Lists.newArrayList(viewport, language, titleMeta, description);

    String author = "author";
    LocalDate date = LocalDate.now();
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

    BDDMockito.doReturn(decoratorBack).when(displayFactory).computeDecoratorBackTileName(Mockito.eq(locale));
    BDDMockito.doReturn(tile).when(displayFactory).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(displayFactory).computeMetaElements(Mockito.eq(locale));
    BDDMockito.doReturn(backMenu).when(displayFactory)
        .computeBackMenuItems(Mockito.any(BACK_PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(Mockito.eq(locale));
    BDDMockito.doReturn(request).when(displayFactory).computeNewsEntryRequest(Mockito.any(NewsEntryDTO.class));
    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(Mockito.anyLong());

    ModelAndView result = displayFactory.computeModelAndViewForOneNewsEntry(BACK_PAGE.NEWS_UPDATE, locale, "123");

    Assert.assertEquals(decoratorBack, result.getViewName());
    Assert.assertEquals(tile, result.getModel().get("content"));

    Mockito.verify(displayFactory, Mockito.times(1)).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMetaElements(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeBackMenuItems(Mockito.any(BACK_PAGE.class),
        Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeHiddenLink(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeNewsEntryRequest(Mockito.any(NewsEntryDTO.class));

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

    MetaElementToDelete viewport = new MetaElementBuilder().name(viewPortName).content(viewPortContent).toMetaElement();
    MetaElementToDelete language = new MetaElementBuilder().name(languageName).content(languageContent).toMetaElement();
    MetaElementToDelete titleMeta = new MetaElementBuilder().name(titleName).content(titleContent).toMetaElement();
    MetaElementToDelete description = new MetaElementBuilder().name(descriptionName).content(descriptionContent)
        .toMetaElement();

    List<MetaElementToDelete> metaElements = Lists.newArrayList(viewport, language, titleMeta, description);

    String author = "author";
    LocalDate date = LocalDate.now();
    String tags = "tag;lol";
    String content = "content";
    String alt = "alt";

    NewsContentDTO newsContent = new NewsContentDTOBuilder().content(content).id(1L).creationDate(date)
        .modificationDate(date).toNewsContentDTO();

    NewsImageDTO newsImage = new NewsImageDTOBuilder().id(1L).creationDate(date).modificationDate(date).alt(alt)
        .toNewsImageDTO();

    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().author(author).creationDate(date).tags(tags).id(1L).title(title)
        .newsContent(newsContent).newsImage(newsImage).modificationDate(date).toNewsEntryDTO();

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy");
    String imageDisplaySrc = "http://cm-pl.com";

    String labelPar = "par";
    String labelLe = "le";
    String labelAccroche = "accroche";
    String labelShowHref = "/pages/actualites/666";

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(newsEntry, imageDisplaySrc, labelPar, labelLe,
        dateFormat, labelAccroche, labelShowHref);

    PageWrapper<NewsEntryDisplayBean> pageWrapper = new PageWrapper<>();
    pageWrapper.setPage(new PageImpl<>(Lists.newArrayList(displayBean)));

    BDDMockito.doReturn(25).when(contextHolder).getElementsPerPage();
    BDDMockito.doReturn(decoratorBack).when(displayFactory).computeDecoratorBackTileName(Mockito.eq(locale));
    BDDMockito.doReturn(tile).when(displayFactory).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(displayFactory).computeMetaElements(Mockito.eq(locale));
    BDDMockito.doReturn(backMenu).when(displayFactory)
        .computeBackMenuItems(Mockito.any(BACK_PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(Mockito.eq(locale));
    BDDMockito.doReturn(pageWrapper).when(displayFactory)
        .computePageWrapperOfNews(Mockito.eq(locale), Mockito.anyInt());

    ModelAndView result = displayFactory.computeModelAndViewForBackPage(BACK_PAGE.NEWS_VIEW, locale, 0);

    Assert.assertEquals(decoratorBack, result.getViewName());
    Assert.assertEquals(tile, result.getModel().get("content"));

    Mockito.verify(displayFactory, Mockito.times(1)).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMetaElements(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeBackMenuItems(Mockito.any(BACK_PAGE.class),
        Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeHiddenLink(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computePageWrapperOfNews(Mockito.eq(locale), Mockito.anyInt());
  }

  @Test
  public void testComputeModelAndViewForBackPageCreateNews() throws Exception {
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

    String author = "author";
    LocalDate date = LocalDate.now();
    String tags = "tag;lol";
    String content = "content";
    String alt = "alt";

    NewsContentDTO newsContent = new NewsContentDTOBuilder().content(content).id(1L).creationDate(date)
        .modificationDate(date).toNewsContentDTO();

    NewsImageDTO newsImage = new NewsImageDTOBuilder().id(1L).creationDate(date).modificationDate(date).alt(alt)
        .toNewsImageDTO();

    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().author(author).creationDate(date).tags(tags).id(1L).title(title)
        .newsContent(newsContent).newsImage(newsImage).modificationDate(date).toNewsEntryDTO();

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy");
    String imageDisplaySrc = "http://cm-pl.com";

    String labelPar = "par";
    String labelLe = "le";
    String labelAccroche = "accroche";
    String labelShowHref = "/pages/actualites/666";

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(newsEntry, imageDisplaySrc, labelPar, labelLe,
        dateFormat, labelAccroche, labelShowHref);

    BDDMockito.doReturn(decoratorBack).when(displayFactory).computeDecoratorBackTileName(Mockito.eq(locale));
    BDDMockito.doReturn(tile).when(displayFactory).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(metaElements).when(displayFactory).computeMetaElements(Mockito.eq(locale));
    BDDMockito.doReturn(backMenu).when(displayFactory)
        .computeBackMenuItems(Mockito.any(BACK_PAGE.class), Mockito.eq(locale));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList(displayBean)).when(displayFactory)
        .computeNewsEntryDisplayBeans(Mockito.eq(locale));

    ModelAndView result = displayFactory.computeModelAndViewForBackPageCreateNews(BACK_PAGE.NEWS_VIEW, locale);

    Assert.assertEquals(decoratorBack, result.getViewName());
    Assert.assertEquals(tile, result.getModel().get("content"));

    Mockito.verify(displayFactory, Mockito.times(1)).computeTileName(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeMetaElements(Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeBackMenuItems(Mockito.any(BACK_PAGE.class),
        Mockito.eq(locale));
    Mockito.verify(displayFactory, Mockito.times(1)).computeHiddenLink(Mockito.eq(locale));
  }

  @Test
  public void testComputeNewsContentRequest() throws Exception {
    LocalDate date = LocalDate.now();
    NewsContentDTO newsContent = new NewsContentDTOBuilder().id(123456789L).content("someContent").creationDate(date)
        .modificationDate(date).toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().newsContent(newsContent).toNewsEntryDTO();

    NewsContentRequest result = displayFactory.computeNewsContentRequest(newsEntry);

    Assert.assertEquals(newsContent.getContent(), result.getContent());
    Assert.assertEquals(newsContent.getId(), result.getId());
    Assert.assertEquals(newsContent.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(newsContent.getModificationDate(), result.getCreationDate());
  }

  @Test
  public void testComputeNewsImageRequest() throws Exception {
    LocalDate date = LocalDate.now();
    NewsImageDTO newsImage = new NewsImageDTOBuilder().id(123456789L).src("someSrc").creationDate(date)
        .modificationDate(date).alt("someAlt").legend("someLegend").toNewsImageDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().newsImage(newsImage).toNewsEntryDTO();

    BDDMockito.doReturn("http://cm-pl.com/someSrc").when(displayFactory).computeImageSrc(Mockito.eq(newsEntry));
    NewsImageRequest result = displayFactory.computeNewsImageRequest(newsEntry);

    Assert.assertEquals(newsImage.getAlt(), result.getAlt());
    Assert.assertEquals("http://cm-pl.com/someSrc", result.getSrc());
    Assert.assertEquals(newsImage.getLegend(), result.getLegend());
    Assert.assertEquals(newsImage.getId(), result.getId());
    Assert.assertEquals(newsImage.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(newsImage.getModificationDate(), result.getCreationDate());
  }

  @Test
  public void testComputeNewsEntryRequest() throws Exception {

    LocalDate creationDate = LocalDate.now();
    LocalDate modificationDate = LocalDate.now();
    long id = 123456789L;
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().tags("aTag").author("someAuthor").title("someTitle").id(id)
        .creationDate(creationDate).modificationDate(modificationDate).toNewsEntryDTO();

    NewsContentRequest contentRequest = new NewsContentRequestBuilder().id(id).content("someContent")
        .creationDate(creationDate).modificationDate(modificationDate).toNewsContentRequest();
    NewsImageRequest imageRequest = new NewsImageRequestBuilder().id(id).creationDate(creationDate)
        .modificationDate(modificationDate).alt("someAlt").legend("someLegend").src("someSrc").toNewsImageRequest();

    BDDMockito.doReturn(imageRequest).when(displayFactory).computeNewsImageRequest(Mockito.eq(newsEntry));
    BDDMockito.doReturn(contentRequest).when(displayFactory).computeNewsContentRequest(Mockito.eq(newsEntry));
    NewsEntryRequest result = displayFactory.computeNewsEntryRequest(newsEntry);

    Assert.assertEquals(newsEntry.getTags(), result.getTags());
    Assert.assertEquals(newsEntry.getTitle(), result.getTitle());
    Assert.assertEquals(newsEntry.getAuthor(), result.getAuthor());
    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertEquals(newsEntry.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(newsEntry.getModificationDate(), result.getCreationDate());

    NewsContentRequest resultContentRequest = result.getContent();
    Assert.assertEquals(contentRequest, resultContentRequest);

    NewsImageRequest resultImageRequest = result.getImage();
    Assert.assertEquals(imageRequest, resultImageRequest);
  }

  @Test
  public void testComputeNewsRequestForEditForm_No_Image_Content() throws Exception {

    LocalDate creationDate = LocalDate.now();
    LocalDate modificationDate = LocalDate.now();
    long id = 123456789L;

    NewsContentDTO newsContent = new NewsContentDTOBuilder().id(id).content("someContent").creationDate(creationDate)
        .modificationDate(modificationDate).toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(id).newsContent(newsContent).toNewsEntryDTO();

    NewsContentRequest contentRequest = new NewsContentRequestBuilder().id(id).content("someContent")
        .creationDate(creationDate).modificationDate(modificationDate).toNewsContentRequest();
    NewsImageRequest imageRequest = new NewsImageRequestBuilder().toNewsImageRequest();
    NewsEntryRequest newsEntryRequest = new NewsEntryRequestBuilder().id(123456789L).content(contentRequest)
        .image(imageRequest).toNewsEntryRequest();

    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(Mockito.anyLong());
    BDDMockito.doReturn(newsEntryRequest).when(displayFactory).computeNewsEntryRequest(Mockito.eq(newsEntry));

    NewsEntryRequest result = displayFactory.computeNewsRequestForEditForm("123456789");

    Assert.assertEquals(newsEntryRequest, result);
    Assert.assertNull(result.getImage().getId());
    Assert.assertNotNull(result.getContent().getId());

  }

  @Test
  public void testComputeNewsRequestForEditForm_No_Image_No_Content() throws Exception {
    long id = 123456789L;

    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(id).toNewsEntryDTO();
    NewsContentRequest contentRequest = new NewsContentRequestBuilder().toNewsContentRequest();
    NewsImageRequest imageRequest = new NewsImageRequestBuilder().toNewsImageRequest();
    NewsEntryRequest newsEntryRequest = new NewsEntryRequestBuilder().id(id).content(contentRequest)
        .image(imageRequest).toNewsEntryRequest();

    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(Mockito.anyLong());
    BDDMockito.doReturn(newsEntryRequest).when(displayFactory).computeNewsEntryRequest(Mockito.eq(newsEntry));

    NewsEntryRequest result = displayFactory.computeNewsRequestForEditForm("123456789");

    Assert.assertEquals(newsEntryRequest, result);
    Assert.assertNull(result.getImage().getId());
    Assert.assertNull(result.getContent().getId());
  }

  @Test
  public void testComputeNewsRequestForEditForm_Image_No_Content() throws Exception {
    LocalDate creationDate = LocalDate.now();
    LocalDate modificationDate = LocalDate.now();
    long id = 123456789L;

    NewsImageDTO newsImage = new NewsImageDTOBuilder().id(id).toNewsImageDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(id).newsImage(newsImage).toNewsEntryDTO();

    NewsContentRequest contentRequest = new NewsContentRequestBuilder().toNewsContentRequest();
    NewsImageRequest imageRequest = new NewsImageRequestBuilder().id(id).creationDate(creationDate)
        .modificationDate(modificationDate).alt("someAlt").legend("someLegend").src("someSrc").toNewsImageRequest();
    NewsEntryRequest newsEntryRequest = new NewsEntryRequestBuilder().id(id).content(contentRequest)
        .image(imageRequest).toNewsEntryRequest();

    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(Mockito.anyLong());
    BDDMockito.doReturn(newsEntryRequest).when(displayFactory).computeNewsEntryRequest(Mockito.eq(newsEntry));

    NewsEntryRequest result = displayFactory.computeNewsRequestForEditForm("123456789");

    Assert.assertEquals(newsEntryRequest, result);
    Assert.assertNotNull(result.getImage().getId());
    Assert.assertNull(result.getContent().getId());
  }

  @Test
  public void testComputeNewsRequestForEditForm_Image_Content() throws Exception {
    LocalDate creationDate = LocalDate.now();
    LocalDate modificationDate = LocalDate.now();
    long id = 123456789L;

    NewsImageDTO newsImage = new NewsImageDTOBuilder().id(id).toNewsImageDTO();
    NewsContentDTO newsContent = new NewsContentDTOBuilder().id(id).content("someContent").creationDate(creationDate)
        .modificationDate(modificationDate).toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(id).newsImage(newsImage).newsContent(newsContent)
        .toNewsEntryDTO();

    NewsContentRequest contentRequest = new NewsContentRequestBuilder().id(id).content("someContent")
        .creationDate(creationDate).modificationDate(modificationDate).toNewsContentRequest();
    NewsImageRequest imageRequest = new NewsImageRequestBuilder().id(id).creationDate(creationDate)
        .modificationDate(modificationDate).alt("someAlt").legend("someLegend").src("someSrc").toNewsImageRequest();
    NewsEntryRequest newsEntryRequest = new NewsEntryRequestBuilder().id(id).content(contentRequest)
        .image(imageRequest).toNewsEntryRequest();

    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(Mockito.anyLong());
    BDDMockito.doReturn(newsEntryRequest).when(displayFactory).computeNewsEntryRequest(Mockito.eq(newsEntry));

    NewsEntryRequest result = displayFactory.computeNewsRequestForEditForm("123456789");

    Assert.assertEquals(newsEntryRequest, result);
    Assert.assertNotNull(result.getImage().getId());
    Assert.assertNotNull(result.getContent().getId());
  }

  @Test
  public void testComputeImageSrc() throws Exception {

    NewsImageDTO image = new NewsImageDTOBuilder().src("someSrc").toNewsImageDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).toNewsEntryDTO();

    BDDMockito.doReturn("http://cm-pl.com/").when(contextHolder).getImageDisplaySrc();

    String result = displayFactory.computeImageSrc(entry);

    Assert.assertEquals("http://cm-pl.com/someSrc", result);

  }

  @Test
  public void testComputeImageSrc_Null() throws Exception {

    NewsImageDTO image = new NewsImageDTOBuilder().toNewsImageDTO();
    NewsEntryDTO entry = new NewsEntryDTOBuilder().newsImage(image).toNewsEntryDTO();

    String result = displayFactory.computeImageSrc(entry);

    Assert.assertNull(result);

  }
}
