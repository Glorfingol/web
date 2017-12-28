package com.cmpl.web.page;

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
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.builder.PageWrapperBuilder;
import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.model.PageWrapper;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.meta.MetaElementCreateForm;
import com.cmpl.web.meta.MetaElementCreateFormBuilder;
import com.cmpl.web.meta.MetaElementDTO;
import com.cmpl.web.meta.MetaElementDTOBuilder;
import com.cmpl.web.meta.MetaElementService;
import com.cmpl.web.meta.OpenGraphMetaElementCreateForm;
import com.cmpl.web.meta.OpenGraphMetaElementCreateFormBuilder;
import com.cmpl.web.meta.OpenGraphMetaElementDTO;
import com.cmpl.web.meta.OpenGraphMetaElementDTOBuilder;
import com.cmpl.web.meta.OpenGraphMetaElementService;

@RunWith(MockitoJUnitRunner.class)
public class PageManagerDisplayFactoryImplTest {

  @Mock
  private PageService pageService;
  @Mock
  private OpenGraphMetaElementService openGraphMetaElementService;
  @Mock
  private MetaElementService metaElementService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSource messageSource;

  @Spy
  @InjectMocks
  private PageManagerDisplayFactoryImpl displayFactory;

  @Test
  public void testComputeModelAndViewForUpdatePageFooter() throws Exception {

    PageDTO dto = new PageDTOBuilder().build();
    BDDMockito.given(pageService.getEntity(BDDMockito.anyLong())).willReturn(dto);

    PageUpdateForm form = new PageUpdateFormBuilder().build();
    BDDMockito.doReturn(form).when(displayFactory).createUpdateForm(BDDMockito.any(PageDTO.class));

    ModelAndView result = displayFactory.computeModelAndViewForUpdatePageFooter(Locale.FRANCE, "123456789");

    Assert.assertNotNull(result.getModel().get("updateForm"));
  }

  @Test
  public void testComputeModelAndViewForUpdatePageHeader() throws Exception {

    PageDTO dto = new PageDTOBuilder().build();
    BDDMockito.given(pageService.getEntity(BDDMockito.anyLong())).willReturn(dto);

    PageUpdateForm form = new PageUpdateFormBuilder().build();
    BDDMockito.doReturn(form).when(displayFactory).createUpdateForm(BDDMockito.any(PageDTO.class));

    ModelAndView result = displayFactory.computeModelAndViewForUpdatePageHeader(Locale.FRANCE, "123456789");
    Assert.assertNotNull(result.getModel().get("updateForm"));
  }

  @Test
  public void testCreateOpenGraphMetaElementCreateForm() throws Exception {

    String someId = "123456789";
    Assert.assertEquals(someId, displayFactory.createOpenGraphMetaElementCreateForm(someId).getPageId());
  }

  @Test
  public void testCreateMetaElementCreateForm() throws Exception {

    String someId = "123456789";
    Assert.assertEquals(someId, displayFactory.createMetaElementCreateForm(someId).getPageId());
  }

  @Test
  public void testComputeModelAndViewForUpdatePageOpenGraphMeta() throws Exception {

    OpenGraphMetaElementCreateForm form = new OpenGraphMetaElementCreateFormBuilder().build();
    BDDMockito.doReturn(form).when(displayFactory).createOpenGraphMetaElementCreateForm(BDDMockito.anyString());

    OpenGraphMetaElementDTO openGraphMeta = new OpenGraphMetaElementDTOBuilder().build();
    BDDMockito.given(openGraphMetaElementService.findOpenGraphMetaElementsByPageId(BDDMockito.anyString())).willReturn(
        Lists.newArrayList(openGraphMeta));

    ModelAndView result = displayFactory.computeModelAndViewForUpdatePageOpenGraphMeta(Locale.FRANCE, "123456789");
    Assert.assertNotNull(result.getModel().get("metaElements"));
    Assert.assertNotNull(result.getModel().get("createForm"));

  }

  @Test
  public void testComputeModelAndViewForUpdatePageMeta() throws Exception {
    MetaElementCreateForm form = new MetaElementCreateFormBuilder().build();
    BDDMockito.doReturn(form).when(displayFactory).createMetaElementCreateForm(BDDMockito.anyString());

    MetaElementDTO meta = new MetaElementDTOBuilder().build();
    BDDMockito.given(metaElementService.findMetaElementsByPageId(BDDMockito.anyString())).willReturn(
        Lists.newArrayList(meta));

    ModelAndView result = displayFactory.computeModelAndViewForUpdatePageMeta(Locale.FRANCE, "123456789");
    Assert.assertNotNull(result.getModel().get("metaElements"));
    Assert.assertNotNull(result.getModel().get("createForm"));

  }

  @Test
  public void testComputeModelAndViewForUpdatePageBody() throws Exception {

    PageDTO dto = new PageDTOBuilder().build();
    BDDMockito.given(pageService.getEntity(BDDMockito.anyLong())).willReturn(dto);

    PageUpdateForm form = new PageUpdateFormBuilder().build();
    BDDMockito.doReturn(form).when(displayFactory).createUpdateForm(BDDMockito.any(PageDTO.class));

    ModelAndView result = displayFactory.computeModelAndViewForUpdatePageBody(Locale.FRANCE, "123456789");
    Assert.assertNotNull(result.getModel().get("updateForm"));
  }

  @Test
  public void testComputeModelAndViewForUpdatePageMain() throws Exception {

    PageDTO dto = new PageDTOBuilder().build();
    BDDMockito.given(pageService.getEntity(BDDMockito.anyLong())).willReturn(dto);

    PageUpdateForm form = new PageUpdateFormBuilder().build();
    BDDMockito.doReturn(form).when(displayFactory).createUpdateForm(BDDMockito.any(PageDTO.class));

    ModelAndView result = displayFactory.computeModelAndViewForUpdatePageMain(Locale.FRANCE, "123456789");
    Assert.assertNotNull(result.getModel().get("updateForm"));
  }

  @Test
  public void testComputeModelAndViewForUpdatePage() throws Exception {

    PageDTO dto = new PageDTOBuilder().build();
    BDDMockito.given(pageService.getEntity(BDDMockito.anyLong())).willReturn(dto);

    PageUpdateForm form = new PageUpdateFormBuilder().build();
    BDDMockito.doReturn(form).when(displayFactory).createUpdateForm(BDDMockito.any(PageDTO.class));

    ModelAndView result = displayFactory.computeModelAndViewForUpdatePage(Locale.FRANCE, "123456789");
    Assert.assertNotNull(result.getModel().get("updateForm"));
  }

  @Test
  public void testComputeModelAndViewForCreatePage() throws Exception {

    PageCreateForm form = new PageCreateFormBuilder().build();
    BDDMockito.doReturn(form).when(displayFactory).computeCreateForm();

    ModelAndView result = displayFactory.computeModelAndViewForCreatePage(Locale.FRANCE);
    Assert.assertNotNull(result.getModel().get("createForm"));
  }

  @Test
  public void testCreateUpdateForm() throws Exception {
    PageDTO dto = new PageDTOBuilder().body("someBody").footer("someFooter").header("someHeader").build();

    PageUpdateForm result = displayFactory.createUpdateForm(dto);
    Assert.assertEquals(dto.getBody(), result.getBody());
    Assert.assertEquals(dto.getHeader(), result.getHeader());
    Assert.assertEquals(dto.getFooter(), result.getFooter());
  }

  @Test
  public void testComputeModelAndViewForViewAllPages() throws Exception {

    List<PageDTO> pages = new ArrayList<>();
    PageDTO pageDTO = new PageDTOBuilder().build();
    pages.add(pageDTO);
    PageImpl<PageDTO> page = new PageImpl<>(pages);
    boolean isFirstPage = true;
    boolean isLastPage = true;
    int totalPages = 1;
    int currentPageNumber = 1;
    PageWrapper<PageDTO> wrapper = new PageWrapperBuilder<PageDTO>().currentPageNumber(currentPageNumber)
        .firstPage(isFirstPage).lastPage(isLastPage).page(page).totalPages(totalPages).pageBaseUrl("/manager/pages")
        .pageLabel("someLabel").build();

    BDDMockito.doReturn(wrapper).when(displayFactory)
        .computePageWrapper(BDDMockito.any(Locale.class), BDDMockito.anyInt());

    ModelAndView result = displayFactory.computeModelAndViewForViewAllPages(Locale.FRANCE, 0);
    Assert.assertNotNull(result.getModel().get("wrappedPages"));

  }

  @Test
  public void testComputeEntries_Not_Empty() throws Exception {

    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<PageDTO> pages = new ArrayList<>();
    PageDTO pageDTO = new PageDTOBuilder().build();
    pages.add(pageDTO);
    PageImpl<PageDTO> page = new PageImpl<>(pages);
    BDDMockito.given(pageService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(page);

    Page<PageDTO> result = displayFactory.computeEntries(Locale.FRANCE, 1);
    Assert.assertEquals(6, result.getTotalElements());
  }

  @Test
  public void testComputeEntries_Empty() throws Exception {

    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<PageDTO> pages = new ArrayList<>();
    PageImpl<PageDTO> page = new PageImpl<>(pages);
    BDDMockito.given(pageService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(page);

    Page<PageDTO> result = displayFactory.computeEntries(Locale.FRANCE, 1);
    Assert.assertTrue(CollectionUtils.isEmpty(result.getContent()));
  }

  @Test
  public void testComputePageWrapperOfPages() throws Exception {

    List<PageDTO> pages = new ArrayList<>();
    PageDTO pageDTO = new PageDTOBuilder().build();
    pages.add(pageDTO);
    PageImpl<PageDTO> page = new PageImpl<>(pages);

    String pageLabel = "Page 1";

    BDDMockito.doReturn(page).when(displayFactory).computeEntries(BDDMockito.any(Locale.class), BDDMockito.anyInt());
    BDDMockito.doReturn(pageLabel).when(displayFactory)
        .getI18nValue(BDDMockito.anyString(), BDDMockito.any(Locale.class), BDDMockito.anyInt(), BDDMockito.anyInt());
    PageWrapper<PageDTO> wrapper = displayFactory.computePageWrapper(Locale.FRANCE, 1);

    Assert.assertEquals(0, wrapper.getCurrentPageNumber());
    Assert.assertTrue(wrapper.isFirstPage());
    Assert.assertTrue(wrapper.isLastPage());
    Assert.assertEquals(page, wrapper.getPage());
    Assert.assertEquals(1, wrapper.getTotalPages());
    Assert.assertEquals("/manager/pages", wrapper.getPageBaseUrl());
    Assert.assertEquals(pageLabel, wrapper.getPageLabel());
  }
}
