package com.cmpl.web.news;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.model.PageWrapper;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.menu.MenuItem;
import com.cmpl.web.menu.MenuItemBuilder;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class NewsManagerDisplayFactoryImplTest {

  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSourceImpl messageSource;
  @Mock
  private NewsEntryService newsEntryService;
  @Mock
  private ContextHolder contextHolder;

  @InjectMocks
  @Spy
  private NewsManagerDisplayFactoryImpl displayFactory;

  @Test
  public void testComputeNewsEditBeanDisplayBean() throws Exception {

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy");
    String imageDisplaySrc = "http://cm-pl.com";

    String autor = "author";
    LocalDate date = LocalDate.now();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().author(autor).creationDate(date).id(1L).build();

    String labelPar = "par";
    String labelLe = "le";
    String labelAccroche = "accroche";
    String labelShowHref = "/pages/actualites/666";

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(newsEntry, imageDisplaySrc, labelPar, labelLe,
        dateFormat, labelAccroche, labelShowHref);

    BDDMockito.doReturn(displayBean).when(displayFactory)
        .computeNewsEntryDisplayBean(BDDMockito.eq(Locale.FRANCE), BDDMockito.eq(newsEntry));

    NewsEntryDisplayBean result = displayFactory.computeNewsEntryDisplayBean(Locale.FRANCE, newsEntry);

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

    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().author(autor).creationDate(date).id(1L).build();
    List<NewsEntryDTO> newsEntries = Lists.newArrayList(newsEntry);

    BDDMockito.doReturn(formatted).when(contextHolder).getDateFormat();
    BDDMockito.doReturn(newsEntries).when(newsEntryService).getEntities();
    BDDMockito.doReturn(labelPar).when(displayFactory)
        .getI18nValue(BDDMockito.eq("news.entry.by"), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(labelLe).when(displayFactory)
        .getI18nValue(BDDMockito.eq("news.entry.the"), BDDMockito.eq(Locale.FRANCE));

    List<NewsEntryDisplayBean> result = displayFactory.computeNewsEntryDisplayBeans(Locale.FRANCE);

    Assert.assertFalse(CollectionUtils.isEmpty(result));
    Assert.assertTrue(newsEntry.getId() == Long.valueOf(result.get(0).getNewsEntryId()));
    Assert.assertEquals(labelPar + " " + autor + " " + labelLe + " " + formattedDate, result.get(0).getPanelHeading());

  }

  @Test
  public void testComputeNewsEntryDisplayBeans_Empty() throws Exception {

    BDDMockito.doReturn(Lists.newArrayList()).when(newsEntryService).getEntities();

    List<NewsEntryDisplayBean> result = displayFactory.computeNewsEntryDisplayBeans(Locale.FRANCE);

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
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Lists.newArrayList(index, news);

    String author = "author";
    LocalDate date = LocalDate.now();
    String tags = "tag;lol";
    String content = "content";
    String alt = "alt";

    NewsEntryRequest request = NewsEntryRequestBuilder.create().build();

    NewsContentDTO newsContent = NewsContentDTOBuilder.create().content(content).id(1L).creationDate(date)
        .modificationDate(date).build();

    NewsImageDTO newsImage = NewsImageDTOBuilder.create().alt(alt).id(1L).creationDate(date).modificationDate(date)
        .build();

    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().author(author).tags(tags).title(title)
        .newsContent(newsContent).newsImage(newsImage).id(1L).creationDate(date).modificationDate(date).build();

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));

    BDDMockito.doReturn(decoratorBack).when(displayFactory).computeDecoratorBackTileName(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(tile).when(displayFactory)
        .computeTileName(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(backMenu).when(displayFactory)
        .computeBackMenuItems(BDDMockito.any(BACK_PAGE.class), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(request).when(displayFactory).computeNewsEntryRequest(BDDMockito.any(NewsEntryDTO.class));
    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(BDDMockito.anyLong());

    ModelAndView result = displayFactory.computeModelAndViewForOneNewsEntry(Locale.FRANCE, "123");

    Assert.assertEquals(decoratorBack, result.getViewName());
    Assert.assertEquals(tile, result.getModel().get("content"));

    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeTileName(BDDMockito.anyString(),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeBackMenuItems(BDDMockito.any(BACK_PAGE.class),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeNewsEntryRequest(BDDMockito.any(NewsEntryDTO.class));

  }

  @Test
  public void testComputeModelAndViewForBackPage() throws Exception {
    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Lists.newArrayList(index, news);

    String author = "author";
    LocalDate date = LocalDate.now();
    String tags = "tag;lol";
    String content = "content";
    String alt = "alt";

    NewsContentDTO newsContent = NewsContentDTOBuilder.create().content(content).id(1L).creationDate(date)
        .modificationDate(date).build();

    NewsImageDTO newsImage = NewsImageDTOBuilder.create().alt(alt).id(1L).creationDate(date).modificationDate(date)
        .build();

    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().author(author).tags(tags).title(title)
        .newsContent(newsContent).newsImage(newsImage).id(1L).creationDate(date).modificationDate(date).build();

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

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));

    BDDMockito.doReturn(tile).when(displayFactory)
        .computeTileName(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(backMenu).when(displayFactory)
        .computeBackMenuItems(BDDMockito.any(BACK_PAGE.class), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(pageWrapper).when(displayFactory)
        .computePageWrapper(BDDMockito.eq(Locale.FRANCE), BDDMockito.anyInt());

    ModelAndView result = displayFactory.computeModelAndViewForBackPage(Locale.FRANCE, 0);

    Assert.assertEquals(tile, result.getModel().get("content"));

    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeTileName(BDDMockito.anyString(),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeBackMenuItems(BDDMockito.any(BACK_PAGE.class),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computePageWrapper(BDDMockito.eq(Locale.FRANCE),
        BDDMockito.anyInt());
  }

  @Test
  public void testComputeModelAndViewForBackPageCreateNews() throws Exception {
    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorBack = "decorator_back";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Lists.newArrayList(index, news);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));

    BDDMockito.doReturn(decoratorBack).when(displayFactory).computeDecoratorBackTileName(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(tile).when(displayFactory)
        .computeTileName(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(backMenu).when(displayFactory)
        .computeBackMenuItems(BDDMockito.any(BACK_PAGE.class), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));

    ModelAndView result = displayFactory.computeModelAndViewForBackPageCreateNews(Locale.FRANCE);

    Assert.assertEquals(decoratorBack, result.getViewName());
    Assert.assertEquals(tile, result.getModel().get("content"));

    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeTileName(BDDMockito.anyString(),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeBackMenuItems(BDDMockito.any(BACK_PAGE.class),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
  }

  @Test
  public void testComputeNewsContentRequest() throws Exception {
    LocalDate date = LocalDate.now();
    NewsContentDTO newsContent = NewsContentDTOBuilder.create().content("someContent").id(123456789L)
        .creationDate(date).modificationDate(date).build();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().newsContent(newsContent).build();

    NewsContentRequest result = displayFactory.computeNewsContentRequest(newsEntry);

    Assert.assertEquals(newsContent.getContent(), result.getContent());
    Assert.assertEquals(newsContent.getId(), result.getId());
    Assert.assertEquals(newsContent.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(newsContent.getModificationDate(), result.getCreationDate());
  }

  @Test
  public void testComputeNewsImageRequest() throws Exception {
    LocalDate date = LocalDate.now();
    NewsImageDTO newsImage = NewsImageDTOBuilder.create().src("someSrc").alt("someAlt").legend("someLegend")
        .id(123456789L).creationDate(date).modificationDate(date).build();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().newsImage(newsImage).build();

    BDDMockito.doReturn("http://cm-pl.com/someSrc").when(displayFactory).computeImageSrc(BDDMockito.eq(newsEntry));
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
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().tags("aTag").author("someAuthor").title("someTitle").id(id)
        .creationDate(creationDate).modificationDate(modificationDate).build();

    NewsContentRequest contentRequest = NewsContentRequestBuilder.create().id(id).content("someContent")
        .creationDate(creationDate).modificationDate(modificationDate).build();
    NewsImageRequest imageRequest = NewsImageRequestBuilder.create().id(id).creationDate(creationDate)
        .modificationDate(modificationDate).alt("someAlt").legend("someLegend").src("someSrc").build();

    BDDMockito.doReturn(imageRequest).when(displayFactory).computeNewsImageRequest(BDDMockito.eq(newsEntry));
    BDDMockito.doReturn(contentRequest).when(displayFactory).computeNewsContentRequest(BDDMockito.eq(newsEntry));
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

    NewsContentDTO newsContent = NewsContentDTOBuilder.create().content("someContent").id(id)
        .creationDate(creationDate).modificationDate(modificationDate).build();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().newsContent(newsContent).id(id).build();

    NewsContentRequest contentRequest = NewsContentRequestBuilder.create().id(id).content("someContent")
        .creationDate(creationDate).modificationDate(modificationDate).build();
    NewsImageRequest imageRequest = NewsImageRequestBuilder.create().build();
    NewsEntryRequest newsEntryRequest = NewsEntryRequestBuilder.create().id(123456789L).content(contentRequest)
        .image(imageRequest).build();

    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(BDDMockito.anyLong());
    BDDMockito.doReturn(newsEntryRequest).when(displayFactory).computeNewsEntryRequest(BDDMockito.eq(newsEntry));

    NewsEntryRequest result = displayFactory.computeNewsRequestForEditForm("123456789");

    Assert.assertEquals(newsEntryRequest, result);
    Assert.assertNull(result.getImage().getId());
    Assert.assertNotNull(result.getContent().getId());

  }

  @Test
  public void testComputeNewsRequestForEditForm_No_Image_No_Content() throws Exception {
    long id = 123456789L;

    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().id(id).build();
    NewsContentRequest contentRequest = NewsContentRequestBuilder.create().build();
    NewsImageRequest imageRequest = NewsImageRequestBuilder.create().build();
    NewsEntryRequest newsEntryRequest = NewsEntryRequestBuilder.create().id(id).content(contentRequest)
        .image(imageRequest).build();

    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(BDDMockito.anyLong());
    BDDMockito.doReturn(newsEntryRequest).when(displayFactory).computeNewsEntryRequest(BDDMockito.eq(newsEntry));

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

    NewsImageDTO newsImage = NewsImageDTOBuilder.create().id(id).build();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().newsImage(newsImage).id(id).build();

    NewsContentRequest contentRequest = NewsContentRequestBuilder.create().build();
    NewsImageRequest imageRequest = NewsImageRequestBuilder.create().id(id).creationDate(creationDate)
        .modificationDate(modificationDate).alt("someAlt").legend("someLegend").src("someSrc").build();
    NewsEntryRequest newsEntryRequest = NewsEntryRequestBuilder.create().id(id).content(contentRequest)
        .image(imageRequest).build();

    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(BDDMockito.anyLong());
    BDDMockito.doReturn(newsEntryRequest).when(displayFactory).computeNewsEntryRequest(BDDMockito.eq(newsEntry));

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

    NewsImageDTO newsImage = NewsImageDTOBuilder.create().id(id).build();
    NewsContentDTO newsContent = NewsContentDTOBuilder.create().content("someContent").id(id)
        .creationDate(creationDate).modificationDate(modificationDate).build();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().newsImage(newsImage).newsContent(newsContent).id(id).build();

    NewsContentRequest contentRequest = NewsContentRequestBuilder.create().id(id).content("someContent")
        .creationDate(creationDate).modificationDate(modificationDate).build();
    NewsImageRequest imageRequest = NewsImageRequestBuilder.create().id(id).creationDate(creationDate)
        .modificationDate(modificationDate).alt("someAlt").legend("someLegend").src("someSrc").build();
    NewsEntryRequest newsEntryRequest = NewsEntryRequestBuilder.create().id(id).content(contentRequest)
        .image(imageRequest).build();

    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(BDDMockito.anyLong());
    BDDMockito.doReturn(newsEntryRequest).when(displayFactory).computeNewsEntryRequest(BDDMockito.eq(newsEntry));

    NewsEntryRequest result = displayFactory.computeNewsRequestForEditForm("123456789");

    Assert.assertEquals(newsEntryRequest, result);
    Assert.assertNotNull(result.getImage().getId());
    Assert.assertNotNull(result.getContent().getId());
  }

  @Test
  public void testComputeImageSrc() throws Exception {

    NewsImageDTO image = NewsImageDTOBuilder.create().src("someSrc").build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsImage(image).build();

    BDDMockito.doReturn("http://cm-pl.com/").when(contextHolder).getImageDisplaySrc();

    String result = displayFactory.computeImageSrc(entry);

    Assert.assertEquals("http://cm-pl.com/someSrc", result);

  }

  @Test
  public void testComputeImageSrc_Null() throws Exception {

    NewsImageDTO image = NewsImageDTOBuilder.create().build();
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().newsImage(image).build();

    String result = displayFactory.computeImageSrc(entry);

    Assert.assertNull(result);

  }
}
