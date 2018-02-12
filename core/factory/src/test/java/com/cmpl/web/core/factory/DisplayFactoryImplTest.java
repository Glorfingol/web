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

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.menu.MenuItemBuilder;
import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsEntryDTOBuilder;
import com.cmpl.web.core.news.NewsEntryDisplayBean;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageDTOBuilder;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.widget.WidgetDTO;
import com.cmpl.web.core.widget.WidgetDTOBuilder;
import com.cmpl.web.core.widget.WidgetPageService;
import com.cmpl.web.core.widget.WidgetService;

@RunWith(MockitoJUnitRunner.class)
public class DisplayFactoryImplTest {

  @Mock
  private MenuFactory menuFactory;

  @Mock
  private PageService pageService;
  @Mock
  private NewsEntryService newsEntryService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private WidgetPageService widgetPageService;
  @Mock
  private WidgetService widgetService;

  @Spy
  @InjectMocks
  private DisplayFactoryImpl displayFactory;

  @Test
  public void testComputeNewsEntries() throws Exception {

    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<NewsEntryDTO> news = new ArrayList<>();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().id(123456789l).build();
    news.add(newsEntry);
    PageImpl<NewsEntryDTO> pageImpl = new PageImpl<>(news);

    BDDMockito.given(newsEntryService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(pageImpl);

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(newsEntry, "", "", null);
    BDDMockito.doReturn(displayBean).when(displayFactory)
        .computeNewsEntryDisplayBean(BDDMockito.any(Locale.class), BDDMockito.any(NewsEntryDTO.class));

    Page<NewsEntryDisplayBean> result = displayFactory.computeNewsEntries(Locale.FRANCE, 0);
    Assert.assertTrue(pageImpl.getContent().get(0).getId() == Long.parseLong(result.getContent().get(0)
        .getNewsEntryId()));

  }

  @Test
  public void testComputePageWrapperOfNews() throws Exception {

    List<NewsEntryDisplayBean> news = new ArrayList<>();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().id(123456789l).build();

    NewsEntryDisplayBean displayBean = new NewsEntryDisplayBean(newsEntry, "", "", null);
    news.add(displayBean);
    PageImpl<NewsEntryDisplayBean> pageImpl = new PageImpl<>(news);

    String pageLabel = "Page 1";

    WidgetDTO widget = WidgetDTOBuilder.create().name("test").build();

    BDDMockito.doReturn(pageImpl).when(displayFactory)
        .computeNewsEntries(BDDMockito.any(Locale.class), BDDMockito.anyInt());
    BDDMockito.doReturn(pageLabel).when(displayFactory)
        .getI18nValue(BDDMockito.anyString(), BDDMockito.any(Locale.class), BDDMockito.anyInt(), BDDMockito.anyInt());
    PageWrapper<NewsEntryDisplayBean> wrapper = displayFactory.computePageWrapperOfNews(widget, Locale.FRANCE, 1);

    Assert.assertEquals(0, wrapper.getCurrentPageNumber());
    Assert.assertTrue(wrapper.isFirstPage());
    Assert.assertTrue(wrapper.isLastPage());
    Assert.assertTrue(pageImpl.getContent().get(0) == wrapper.getPage().getContent().get(0));
    Assert.assertEquals(1, wrapper.getTotalPages());
    Assert.assertEquals("/widgets/test", wrapper.getPageBaseUrl());
    Assert.assertEquals(pageLabel, wrapper.getPageLabel());
  }

  @Test
  public void testComputeMenuItems() throws Exception {
    List<MenuItem> items = new ArrayList<>();
    MenuItem item = MenuItemBuilder.create().label("test").build();
    items.add(item);
    BDDMockito.given(menuFactory.computeMenuItems(BDDMockito.any(PageDTO.class), BDDMockito.any(Locale.class)))
        .willReturn(items);

    List<MenuItem> result = displayFactory.computeMenuItems(new PageDTO(), Locale.FRANCE);
    Assert.assertEquals(item, result.get(0));
  }

  @Test
  public void testComputePageFooter() throws Exception {

    PageDTO page = PageDTOBuilder.create().build();
    page.setName("test");

    Assert.assertEquals("test_footer_fr", displayFactory.computePageFooter(page, Locale.FRANCE));
  }

  @Test
  public void testComputePageHeader() throws Exception {

    PageDTO page = PageDTOBuilder.create().build();
    page.setName("test");

    Assert.assertEquals("test_header_fr", displayFactory.computePageHeader(page, Locale.FRANCE));
  }

  @Test
  public void testComputePageContent() throws Exception {

    PageDTO page = PageDTOBuilder.create().build();
    page.setName("test");

    Assert.assertEquals("test_fr", displayFactory.computePageContent(page, Locale.FRANCE));
  }

  @Test
  public void testComputeModelAndViewForPage_Without_News() throws Exception {
    PageDTO page = PageDTOBuilder.create().id(123456789l).build();

    BDDMockito.given(pageService.getPageByName(BDDMockito.anyString(), BDDMockito.anyString())).willReturn(page);

    BDDMockito.doReturn("test_footer_fr").when(displayFactory)
        .computePageFooter(BDDMockito.any(PageDTO.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn("test_header_fr").when(displayFactory)
        .computePageHeader(BDDMockito.any(PageDTO.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn("someLink").when(displayFactory).computeHiddenLink(BDDMockito.any(Locale.class));

    ModelAndView result = displayFactory.computeModelAndViewForPage("somePage", Locale.FRANCE, 0);

    Assert.assertEquals("test_footer_fr", result.getModel().get("footerTemplate"));
    Assert.assertEquals("test_header_fr", result.getModel().get("header"));
    Assert.assertEquals("someLink", result.getModel().get("hiddenLink"));
    Assert.assertNull(result.getModel().get("wrappedNews"));
    Assert.assertNull(result.getModel().get("emptyMessage"));

    BDDMockito.verify(displayFactory, BDDMockito.times(0)).computePageWrapperOfNews(BDDMockito.any(WidgetDTO.class),
        BDDMockito.any(Locale.class), BDDMockito.anyInt());
  }

}
