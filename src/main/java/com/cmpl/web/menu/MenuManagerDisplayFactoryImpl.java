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
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.model.PageWrapper;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;
import com.cmpl.web.page.PageDTO;
import com.cmpl.web.page.PageService;

public class MenuManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<MenuDTO> implements
    MenuManagerDisplayFactory {

  private final MenuService menuService;
  private final PageService pageService;
  private final ContextHolder contextHolder;

  private static final String CREATE_FORM = "createForm";
  private static final String UPDATE_FORM = "updateForm";
  private static final String WRAPPED_MENUS = "wrappedMenus";
  private static final String MENUS_PARENTS = "menusThatCanBeParents";
  private static final String PAGES_LINKABLE = "pagesThatCanBeLinkedTo";

  public MenuManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      MenuService menuService, PageService pageService, ContextHolder contextHolder) {
    super(menuFactory, messageSource);
    this.menuService = menuService;
    this.contextHolder = contextHolder;
    this.pageService = pageService;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllMenus(Locale locale, int pageNumber) {
    ModelAndView menusManager = super.computeModelAndViewForBackPage(BACK_PAGE.MENUS_VIEW, locale);
    LOGGER.info("Construction des menus pour la page " + BACK_PAGE.MENUS_VIEW.name());

    PageWrapper<MenuDTO> pagedPageDTOWrapped = computePageWrapper(locale, pageNumber);

    menusManager.addObject(WRAPPED_MENUS, pagedPageDTOWrapped);

    return menusManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateMenu(Locale locale) {
    ModelAndView menusManager = super.computeModelAndViewForBackPage(BACK_PAGE.MENUS_CREATE, locale);
    LOGGER.info("Construction d'un menu pour la page " + BACK_PAGE.MENUS_CREATE.name());

    List<MenuDTO> menusThatCanBeParents = menuService.getMenus();
    menusManager.addObject(MENUS_PARENTS, menusThatCanBeParents);

    List<PageDTO> pagesThatCanBeLinkedTo = pageService.getPages();
    menusManager.addObject(PAGES_LINKABLE, pagesThatCanBeLinkedTo);

    MenuCreateForm createForm = new MenuCreateFormBuilder().build();
    menusManager.addObject(CREATE_FORM, createForm);

    return menusManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateMenu(Locale locale, String menuId) {
    ModelAndView menusManager = super.computeModelAndViewForBackPage(BACK_PAGE.MENUS_UPDATE, locale);
    LOGGER.info("Construction d'un menu pour la page " + BACK_PAGE.MENUS_UPDATE.name());

    List<MenuDTO> menus = menuService.getMenus();
    List<MenuDTO> menusThatCanBeParents = new ArrayList<>();
    menus.forEach(menu -> {
      if (!menuId.equals(String.valueOf(menu.getId()))) {
        menusThatCanBeParents.add(menu);
      }
    });
    menusManager.addObject(MENUS_PARENTS, menusThatCanBeParents);

    List<PageDTO> pagesThatCanBeLinkedTo = pageService.getPages();
    menusManager.addObject(PAGES_LINKABLE, pagesThatCanBeLinkedTo);

    MenuDTO menuToUpdate = menuService.getEntity(Long.valueOf(menuId));
    MenuUpdateForm updateForm = new MenuUpdateForm(menuToUpdate);

    menusManager.addObject(UPDATE_FORM, updateForm);

    return menusManager;
  }

  @Override
  protected Page<MenuDTO> computeEntries(Locale locale, int pageNumber) {
    List<MenuDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = new PageRequest(pageNumber, contextHolder.getElementsPerPage());
    Page<MenuDTO> pagedMenuDTOEntries = menuService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedMenuDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedMenuDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedMenuDTOEntries.getTotalElements());
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/menus";
  }

}
