package com.cmpl.web.core.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.carousel.CarouselDTO;
import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsEntryDisplayBean;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageService;

@RunWith(MockitoJUnitRunner.class)
public class DisplayFactoryImplTest {

  @Mock
  private MenuFactory menuFactory;
  @Mock
  private CarouselService carouselService;
  @Mock
  private PageService pageService;
  @Mock
  private NewsEntryService newsEntryService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private FileService fileService;

  @Spy
  @InjectMocks
  private DisplayFactoryImpl displayFactory;

  @Test
  public void testComputeNewsEntryDisplayBeanPageDTOLocaleNewsEntryDTO() throws Exception {
    PageDTO page = new PageDTO();
    page.setName("someName");

    NewsEntryDTO newsEntryDTO = new NewsEntryDTO();
    newsEntryDTO.setId(123456789l);

    BDDMockito.given(contextHolder.getImageDisplaySrc()).willReturn("someSrc");
    BDDMockito.doReturn("someString").when(displayFactory)
        .getI18nValue(BDDMockito.anyString(), BDDMockito.any(Locale.class));

    NewsEntryDisplayBean result = displayFactory.computeNewsEntryDisplayBean(page, Locale.FRANCE, newsEntryDTO);
    Assert.assertEquals("/pages/someName/123456789", result.getNewsEntryShowHref());
  }

  @Test
  public void testComputeNewsEntry() throws Exception {

    NewsEntryDTO newsEntry = new NewsEntryDTO();
    BDDMockito.given(newsEntryService.getEntity(BDDMockito.anyLong())).willReturn(newsEntry);

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(newsEntry, "", "", "", null, "", "test");
    BDDMockito
        .doReturn(displayBean)
        .when(displayFactory)
        .computeNewsEntryDisplayBean(BDDMockito.any(PageDTO.class), BDDMockito.any(Locale.class),
            BDDMockito.any(NewsEntryDTO.class));

    Assert.assertEquals(displayBean.getNewsEntryShowHref(),
        displayFactory.computeNewsEntry(new PageDTO(), Locale.FRANCE, "123456789").getNewsEntryShowHref());
  }

  @Test
  public void testComputeNewsEntries() throws Exception {

    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<NewsEntryDTO> news = new ArrayList<>();
    NewsEntryDTO newsEntry = new NewsEntryDTO();
    newsEntry.setId(123456789l);
    news.add(newsEntry);
    PageImpl<NewsEntryDTO> pageImpl = new PageImpl<>(news);

    BDDMockito.given(newsEntryService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(pageImpl);

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(newsEntry, "", "", "", null, "", "test");
    BDDMockito
        .doReturn(displayBean)
        .when(displayFactory)
        .computeNewsEntryDisplayBean(BDDMockito.any(PageDTO.class), BDDMockito.any(Locale.class),
            BDDMockito.any(NewsEntryDTO.class));

    Page<NewsEntryDisplayBean> result = displayFactory.computeNewsEntries(new PageDTO(), Locale.FRANCE, 0);
    Assert.assertTrue(pageImpl.getContent().get(0).getId() == Long.parseLong(result.getContent().get(0)
        .getNewsEntryId()));

  }

  @Test
  public void testComputePageWrapperOfNews() throws Exception {

    List<NewsEntryDisplayBean> news = new ArrayList<>();
    NewsEntryDTO newsEntry = new NewsEntryDTO();
    newsEntry.setId(123456789l);

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(newsEntry, "", "", "", null, "", "test");
    news.add(displayBean);
    PageImpl<NewsEntryDisplayBean> pageImpl = new PageImpl<>(news);

    String pageLabel = "Page 1";

    PageDTO page = new PageDTO();
    page.setName("test");

    BDDMockito.doReturn(pageImpl).when(displayFactory)
        .computeNewsEntries(BDDMockito.any(PageDTO.class), BDDMockito.any(Locale.class), BDDMockito.anyInt());
    BDDMockito.doReturn(pageLabel).when(displayFactory)
        .getI18nValue(BDDMockito.anyString(), BDDMockito.any(Locale.class), BDDMockito.anyInt(), BDDMockito.anyInt());
    PageWrapper<NewsEntryDisplayBean> wrapper = displayFactory.computePageWrapperOfNews(page, Locale.FRANCE, 1);

    Assert.assertEquals(0, wrapper.getCurrentPageNumber());
    Assert.assertTrue(wrapper.isFirstPage());
    Assert.assertTrue(wrapper.isLastPage());
    Assert.assertTrue(pageImpl.getContent().get(0) == wrapper.getPage().getContent().get(0));
    Assert.assertEquals(1, wrapper.getTotalPages());
    Assert.assertEquals("/pages/test", wrapper.getPageBaseUrl());
    Assert.assertEquals(pageLabel, wrapper.getPageLabel());
  }

  @Test
  public void testComputeMenuItems() throws Exception {
    List<MenuItem> items = new ArrayList<>();
    MenuItem item = new MenuItem();
    item.setLabel("test");
    items.add(item);
    BDDMockito.given(menuFactory.computeMenuItems(BDDMockito.any(PageDTO.class), BDDMockito.any(Locale.class)))
        .willReturn(items);

    List<MenuItem> result = displayFactory.computeMenuItems(new PageDTO(), Locale.FRANCE);
    Assert.assertEquals(item, result.get(0));
  }

  @Test
  public void testComputePageFooter() throws Exception {

    PageDTO page = new PageDTO();
    page.setName("test");

    Assert.assertEquals("test_footer", displayFactory.computePageFooter(page));
  }

  @Test
  public void testComputePageHeader() throws Exception {

    PageDTO page = new PageDTO();
    page.setName("test");

    Assert.assertEquals("test_header", displayFactory.computePageHeader(page));
  }

  @Test
  public void testComputePageContent() throws Exception {

    PageDTO page = new PageDTO();
    page.setName("test");

    Assert.assertEquals("test", displayFactory.computePageContent(page));
  }

  @Test
  public void testComputeCarouselsForPage() throws Exception {
    List<CarouselDTO> carousels = new ArrayList<>();
    CarouselDTO carousel = new CarouselDTO();
    carousel.setId(123456789l);
    carousels.add(carousel);

    BDDMockito.given(carouselService.findByPageId(BDDMockito.anyString())).willReturn(carousels);

    List<CarouselDTO> result = displayFactory.computeCarouselsForPage(new PageDTO());
    Assert.assertTrue(carousel.getId() == result.get(0).getId());

  }

  @Test
  public void testComputeModelAndViewForPage_Without_News() throws Exception {
    PageDTO page = new PageDTO();
    List<MenuItem> items = new ArrayList<>();
    List<CarouselDTO> carousels = new ArrayList<>();
    CarouselDTO carousel = new CarouselDTO();
    carousel.setName("test");
    carousels.add(carousel);

    BDDMockito.given(pageService.getPageByName(BDDMockito.anyString())).willReturn(page);
    BDDMockito.doReturn("test").when(displayFactory).computePageContent(BDDMockito.any(PageDTO.class));
    BDDMockito.doReturn(items).when(displayFactory)
        .computeMenuItems(BDDMockito.any(PageDTO.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn("test_footer").when(displayFactory).computePageFooter(BDDMockito.any(PageDTO.class));
    BDDMockito.doReturn("test_header").when(displayFactory).computePageHeader(BDDMockito.any(PageDTO.class));
    BDDMockito.doReturn(carousels).when(displayFactory).computeCarouselsForPage(BDDMockito.any(PageDTO.class));
    BDDMockito.doReturn("someLink").when(displayFactory).computeHiddenLink(BDDMockito.any(Locale.class));

    ModelAndView result = displayFactory.computeModelAndViewForPage("somePage", Locale.FRANCE, 0);

    Assert.assertEquals("test", result.getModel().get("content"));
    Assert.assertEquals(items, result.getModel().get("menuItems"));
    Assert.assertEquals("test_footer", result.getModel().get("footerTemplate"));
    Assert.assertEquals("test_header", result.getModel().get("header"));
    Assert.assertEquals("someLink", result.getModel().get("hiddenLink"));
    Assert.assertEquals(carousels.get(0), result.getModel().get("carousel_test"));
    Assert.assertNull(result.getModel().get("wrappedNews"));
    Assert.assertNull(result.getModel().get("emptyMessage"));

    BDDMockito.verify(displayFactory, BDDMockito.times(0)).computePageWrapperOfNews(BDDMockito.any(PageDTO.class),
        BDDMockito.any(Locale.class), BDDMockito.anyInt());
  }

  @Test
  public void testComputeModelAndViewForPage_With_News() throws Exception {

    PageDTO page = new PageDTO();
    page.setWithNews(true);
    List<MenuItem> items = new ArrayList<>();
    List<CarouselDTO> carousels = new ArrayList<>();
    CarouselDTO carousel = new CarouselDTO();
    carousel.setName("test");
    carousels.add(carousel);

    BDDMockito.given(pageService.getPageByName(BDDMockito.anyString())).willReturn(page);
    BDDMockito.doReturn("test").when(displayFactory).computePageContent(BDDMockito.any(PageDTO.class));
    BDDMockito.doReturn(items).when(displayFactory)
        .computeMenuItems(BDDMockito.any(PageDTO.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn("test_footer").when(displayFactory).computePageFooter(BDDMockito.any(PageDTO.class));
    BDDMockito.doReturn("test_header").when(displayFactory).computePageHeader(BDDMockito.any(PageDTO.class));
    BDDMockito.doReturn(carousels).when(displayFactory).computeCarouselsForPage(BDDMockito.any(PageDTO.class));
    BDDMockito.doReturn("someLink").when(displayFactory).computeHiddenLink(BDDMockito.any(Locale.class));

    NewsEntryDTO newsEntry = new NewsEntryDTO();
    newsEntry.setId(123456789l);
    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(newsEntry, "", "", "", null, "", "test");
    List<NewsEntryDisplayBean> displayBeans = new ArrayList<>();
    displayBeans.add(displayBean);
    Page<NewsEntryDisplayBean> pagedNewsEntries = new PageImpl<>(displayBeans);
    PageWrapper<NewsEntryDisplayBean> wrappedNews = new PageWrapper<>();
    wrappedNews.setPage(pagedNewsEntries);
    BDDMockito.doReturn(wrappedNews).when(displayFactory)
        .computePageWrapperOfNews(BDDMockito.any(PageDTO.class), BDDMockito.any(Locale.class), BDDMockito.anyInt());

    BDDMockito.doReturn("emptyMessage").when(displayFactory)
        .getI18nValue(BDDMockito.anyString(), BDDMockito.any(Locale.class));

    ModelAndView result = displayFactory.computeModelAndViewForPage("somePage", Locale.FRANCE, 0);

    Assert.assertEquals("test", result.getModel().get("content"));
    Assert.assertEquals(items, result.getModel().get("menuItems"));
    Assert.assertEquals("test_footer", result.getModel().get("footerTemplate"));
    Assert.assertEquals("test_header", result.getModel().get("header"));
    Assert.assertEquals("someLink", result.getModel().get("hiddenLink"));
    Assert.assertEquals(carousels.get(0), result.getModel().get("carousel_test"));
    Assert.assertEquals(wrappedNews, result.getModel().get("wrappedNews"));
    Assert.assertEquals("emptyMessage", result.getModel().get("emptyMessage"));

    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computePageWrapperOfNews(BDDMockito.any(PageDTO.class),
        BDDMockito.any(Locale.class), BDDMockito.anyInt());
  }
}
