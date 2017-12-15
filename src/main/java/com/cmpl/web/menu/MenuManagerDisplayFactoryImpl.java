package com.cmpl.web.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.factory.BackDisplayFactoryImpl;
import com.cmpl.web.core.model.PageWrapper;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.meta.MetaElementFactory;
import com.cmpl.web.page.BACK_PAGE;
import com.cmpl.web.page.PageDTO;
import com.cmpl.web.page.PageService;

public class MenuManagerDisplayFactoryImpl extends BackDisplayFactoryImpl implements MenuManagerDisplayFactory {

  private final MenuService menuService;
  private final PageService pageService;
  private final ContextHolder contextHolder;

  private static final String CREATE_FORM = "createForm";
  private static final String UPDATE_FORM = "updateForm";
  private static final String MENUS_PARENTS = "menusThatCanBeParents";
  private static final String PAGES_LINKABLE = "pagesThatCanBeLinkedTo";

  public MenuManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSourceImpl messageSource,
      MetaElementFactory metaElementFactory, MenuService menuService, PageService pageService,
      ContextHolder contextHolder) {
    super(menuFactory, messageSource, metaElementFactory);
    this.menuService = menuService;
    this.contextHolder = contextHolder;
    this.pageService = pageService;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllMenus(BACK_PAGE backPage, Locale locale, int pageNumber) {
    ModelAndView menusManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des menus pour la page " + backPage.name());

    PageWrapper<MenuDTO> pagedPageDTOWrapped = computePageWrapperOfMenus(locale, pageNumber);

    menusManager.addObject("wrappedMenus", pagedPageDTOWrapped);

    return menusManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateMenu(BACK_PAGE backPage, Locale locale) {
    ModelAndView menusManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction d'un menu pour la page " + backPage.name());

    List<MenuDTO> menusThatCanBeParents = menuService.getMenus();
    menusManager.addObject(MENUS_PARENTS, menusThatCanBeParents);

    List<PageDTO> pagesThatCanBeLinkedTo = pageService.getPages();
    menusManager.addObject(PAGES_LINKABLE, pagesThatCanBeLinkedTo);

    MenuCreateForm createForm = new MenuCreateForm();
    menusManager.addObject(CREATE_FORM, createForm);

    return menusManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateMenu(BACK_PAGE backPage, Locale locale, String menuId) {
    ModelAndView menusManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction d'un menu pour la page " + backPage.name());

    List<MenuDTO> menus = menuService.getMenus();
    List<MenuDTO> menusThatCanBeParents = new ArrayList<>();
    for (MenuDTO menu : menus) {
      if (!menuId.equals(String.valueOf(menu.getId()))) {
        menusThatCanBeParents.add(menu);
      }
    }
    menusManager.addObject(MENUS_PARENTS, menusThatCanBeParents);

    List<PageDTO> pagesThatCanBeLinkedTo = pageService.getPages();
    menusManager.addObject(PAGES_LINKABLE, pagesThatCanBeLinkedTo);

    MenuDTO menuToUpdate = menuService.getEntity(Long.valueOf(menuId));
    MenuUpdateForm updateForm = new MenuUpdateForm(menuToUpdate);

    menusManager.addObject(UPDATE_FORM, updateForm);

    return menusManager;
  }

  PageWrapper<MenuDTO> computePageWrapperOfMenus(Locale locale, int pageNumber) {
    Page<MenuDTO> pagedPageDTOEntries = computeMenusEntries(pageNumber);

    boolean isFirstPage = pagedPageDTOEntries.isFirst();
    boolean isLastPage = pagedPageDTOEntries.isLast();
    int totalPages = pagedPageDTOEntries.getTotalPages();
    int currentPageNumber = pagedPageDTOEntries.getNumber();

    PageWrapper<MenuDTO> pagedMenuDTOWrapped = new PageWrapper<>();
    pagedMenuDTOWrapped.setCurrentPageNumber(currentPageNumber);
    pagedMenuDTOWrapped.setFirstPage(isFirstPage);
    pagedMenuDTOWrapped.setLastPage(isLastPage);
    pagedMenuDTOWrapped.setPage(pagedPageDTOEntries);
    pagedMenuDTOWrapped.setTotalPages(totalPages);
    pagedMenuDTOWrapped.setPageBaseUrl("/manager/menus");
    pagedMenuDTOWrapped.setPageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages));
    return pagedMenuDTOWrapped;
  }

  Page<MenuDTO> computeMenusEntries(int pageNumber) {
    List<MenuDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = new PageRequest(pageNumber, contextHolder.getElementsPerPage());
    Page<MenuDTO> pagedMenuDTOEntries = menuService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedMenuDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    for (MenuDTO menuDTOFromDB : pagedMenuDTOEntries.getContent()) {
      pageEntries.add(menuDTOFromDB);
    }

    return new PageImpl<>(pageEntries, pageRequest, pagedMenuDTOEntries.getTotalElements());
  }

}
