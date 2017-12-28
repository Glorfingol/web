package com.cmpl.web.menu;

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
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;
import com.cmpl.web.page.PageDTO;
import com.cmpl.web.page.PageDTOBuilder;
import com.cmpl.web.page.PageService;

@RunWith(MockitoJUnitRunner.class)
public class MenuManagerDisplayFactoryImplTest {

  @Mock
  private MenuService menuService;
  @Mock
  private PageService pageService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSource messageSource;

  @Spy
  @InjectMocks
  private MenuManagerDisplayFactoryImpl displayFactory;

  @Test
  public void testComputeEntries_Empty() throws Exception {

    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<MenuDTO> menus = new ArrayList<>();
    PageImpl<MenuDTO> page = new PageImpl<>(menus);
    BDDMockito.given(menuService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(page);

    Page<MenuDTO> result = displayFactory.computeEntries(Locale.FRANCE, 1);
    Assert.assertTrue(CollectionUtils.isEmpty(result.getContent()));

  }

  @Test
  public void testComputeEntries_Not_Empty() throws Exception {

    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<MenuDTO> menus = new ArrayList<>();
    MenuDTO menu = new MenuDTOBuilder().build();
    menus.add(menu);
    PageImpl<MenuDTO> page = new PageImpl<>(menus);
    BDDMockito.given(menuService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(page);

    Page<MenuDTO> result = displayFactory.computeEntries(Locale.FRANCE, 1);
    Assert.assertEquals(6, result.getTotalElements());

  }

  @Test
  public void testComputePageWrapperOfMenus() throws Exception {

    List<MenuDTO> menus = new ArrayList<>();
    MenuDTO menu = new MenuDTOBuilder().build();
    menus.add(menu);
    PageImpl<MenuDTO> page = new PageImpl<>(menus);

    String pageLabel = "Page 1";

    BDDMockito.doReturn(page).when(displayFactory).computeEntries(BDDMockito.any(Locale.class), BDDMockito.anyInt());
    BDDMockito.doReturn(pageLabel).when(displayFactory)
        .getI18nValue(BDDMockito.anyString(), BDDMockito.any(Locale.class), BDDMockito.anyInt(), BDDMockito.anyInt());
    PageWrapper<MenuDTO> wrapper = displayFactory.computePageWrapper(Locale.FRANCE, 1);

    Assert.assertEquals(0, wrapper.getCurrentPageNumber());
    Assert.assertTrue(wrapper.isFirstPage());
    Assert.assertTrue(wrapper.isLastPage());
    Assert.assertEquals(page, wrapper.getPage());
    Assert.assertEquals(1, wrapper.getTotalPages());
    Assert.assertEquals("/manager/menus", wrapper.getPageBaseUrl());
    Assert.assertEquals(pageLabel, wrapper.getPageLabel());

  }

  @Test
  public void testComputeModelAndViewForCreateMenu() throws Exception {
    ModelAndView menusManager = new ModelAndView("test");
    BDDMockito.doReturn(menusManager).when(displayFactory)
        .computeModelAndViewForBackPage(BDDMockito.any(BACK_PAGE.class), BDDMockito.any(Locale.class));

    MenuDTO menu = new MenuDTOBuilder().build();
    BDDMockito.given(menuService.getMenus()).willReturn(Lists.newArrayList(menu));

    PageDTO page = new PageDTOBuilder().build();
    BDDMockito.given(pageService.getPages()).willReturn(Lists.newArrayList(page));

    ModelAndView result = displayFactory.computeModelAndViewForCreateMenu(Locale.FRANCE);
    Assert.assertNotNull(result.getModel().get("menusThatCanBeParents"));
    Assert.assertNotNull(result.getModel().get("pagesThatCanBeLinkedTo"));
    Assert.assertNotNull(result.getModel().get("createForm"));

  }

  @Test
  public void testComputeModelAndViewForViewAllMenus() throws Exception {
    ModelAndView menusManager = new ModelAndView("test");
    BDDMockito.doReturn(menusManager).when(displayFactory)
        .computeModelAndViewForBackPage(BDDMockito.any(BACK_PAGE.class), BDDMockito.any(Locale.class));

    PageWrapper<MenuDTO> wrapper = new PageWrapperBuilder<MenuDTO>().build();
    BDDMockito.doReturn(wrapper).when(displayFactory)
        .computePageWrapper(BDDMockito.any(Locale.class), BDDMockito.anyInt());

    ModelAndView result = displayFactory.computeModelAndViewForViewAllMenus(Locale.FRANCE, 0);
    Assert.assertNotNull(result.getModel().get("wrappedMenus"));

  }

  @Test
  public void testComputeModelAndViewForUpdateMenu() throws Exception {
    MenuDTO possibleParent = new MenuDTOBuilder().id(123456789l).build();
    MenuDTO notPossibleParent = new MenuDTOBuilder().id(12345678l).build();

    BDDMockito.given(menuService.getMenus()).willReturn(Lists.newArrayList(notPossibleParent, possibleParent));

    PageDTO page = new PageDTOBuilder().build();
    BDDMockito.given(pageService.getPages()).willReturn(Lists.newArrayList(page));

    BDDMockito.given(menuService.getEntity(BDDMockito.anyLong())).willReturn(notPossibleParent);

    ModelAndView result = displayFactory.computeModelAndViewForUpdateMenu(Locale.FRANCE, "123456789");
    Assert.assertNotNull(result.getModel().get("menusThatCanBeParents"));
    Assert.assertNotNull(result.getModel().get("pagesThatCanBeLinkedTo"));
    Assert.assertNotNull(result.getModel().get("updateForm"));

  }

}
