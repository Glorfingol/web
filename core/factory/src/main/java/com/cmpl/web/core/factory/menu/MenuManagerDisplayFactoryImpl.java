package com.cmpl.web.core.factory.menu;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.menu.MenuCreateForm;
import com.cmpl.web.core.menu.MenuCreateFormBuilder;
import com.cmpl.web.core.menu.MenuDTO;
import com.cmpl.web.core.menu.MenuService;
import com.cmpl.web.core.menu.MenuUpdateForm;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public class MenuManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<MenuDTO>
    implements MenuManagerDisplayFactory {

  private final MenuService menuService;

  private final PageService pageService;

  private final ContextHolder contextHolder;

  private static final String CREATE_FORM = "createForm";

  private static final String UPDATE_FORM = "updateForm";

  private static final String MENUS_PARENTS = "menusThatCanBeParents";

  private static final String PAGES_LINKABLE = "pagesThatCanBeLinkedTo";

  public MenuManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      MenuService menuService,
      PageService pageService, ContextHolder contextHolder,
      PluginRegistry<BreadCrumb, String> breadCrumbRegistry,
      Set<Locale> availableLocales, GroupService groupService,
      MembershipService membershipService, PluginRegistry<BackPage, String> backPagesRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService,
        membershipService, backPagesRegistry);

    this.menuService = Objects.requireNonNull(menuService);

    this.contextHolder = Objects.requireNonNull(contextHolder);

    this.pageService = Objects.requireNonNull(pageService);

  }

  @Override
  public ModelAndView computeModelAndViewForViewAllMenus(Locale locale, int pageNumber) {
    BackPage backPage = computeBackPage("MENU_VIEW");
    ModelAndView menusManager = super
        .computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des menus pour la page {}", backPage.getPageName());

    PageWrapper<MenuDTO> pagedPageDTOWrapped = computePageWrapper(locale, pageNumber, "");

    menusManager.addObject("wrappedEntities", pagedPageDTOWrapped);

    return menusManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateMenu(Locale locale) {
    BackPage backPage = computeBackPage("MENU_CREATE");
    ModelAndView menusManager = super
        .computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction d'un menu pour la page {}", backPage.getPageName());

    List<MenuDTO> menusThatCanBeParents = menuService.getMenus();
    menusManager.addObject(MENUS_PARENTS, menusThatCanBeParents);

    List<PageDTO> pagesThatCanBeLinkedTo = pageService.getPages();
    menusManager.addObject(PAGES_LINKABLE, pagesThatCanBeLinkedTo);

    MenuCreateForm createForm = MenuCreateFormBuilder.create().build();
    menusManager.addObject(CREATE_FORM, createForm);

    return menusManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateMenuMain(String menuId) {
    ModelAndView menusManager = new ModelAndView("/back/menus/edit/tab_main");

    List<MenuDTO> menusThatCanBeParents = menuService.getMenus().stream()
        .filter(menuDTO -> !menuId.equals(String.valueOf(menuDTO.getId())))
        .collect(Collectors.toList());

    menusManager.addObject(MENUS_PARENTS, menusThatCanBeParents);

    List<PageDTO> pagesThatCanBeLinkedTo = pageService.getPages();
    menusManager.addObject(PAGES_LINKABLE, pagesThatCanBeLinkedTo);

    MenuDTO menuToUpdate = menuService.getEntity(Long.valueOf(menuId));

    MenuUpdateForm updateForm = new MenuUpdateForm(menuToUpdate);

    menusManager.addObject(UPDATE_FORM, updateForm);

    return menusManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateMenu(Locale locale, String menuId) {
    BackPage backPage = computeBackPage("MENU_UPDATE");
    ModelAndView menusManager = super
        .computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction d'un menu pour la page {}", backPage.getPageName());

    MenuDTO menuToUpdate = menuService.getEntity(Long.valueOf(menuId));

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(menuToUpdate.getLabel())
        .build();
    BreadCrumb breadCrumb = (BreadCrumb) menusManager.getModel().get("breadcrumb");

    if (canAddBreadCrumbItem(breadCrumb, item)) {
      breadCrumb.getItems().add(item);
    }

    MenuUpdateForm updateForm = new MenuUpdateForm(menuToUpdate);

    menusManager.addObject(UPDATE_FORM, updateForm);

    return menusManager;
  }

  @Override
  protected Page<MenuDTO> computeEntries(Locale locale, int pageNumber, String query) {
    List<MenuDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<MenuDTO> pagedMenuDTOEntries;
    if (StringUtils.hasText(query)) {
      pagedMenuDTOEntries = menuService
          .searchEntities(PageRequest.of(pageNumber, contextHolder.getElementsPerPage()),
              query);
    } else {
      pagedMenuDTOEntries = menuService
          .getPagedEntities(PageRequest.of(pageNumber, contextHolder.getElementsPerPage()));
    }
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

  @Override
  protected String getItemLink() {
    return "/manager/menus/";
  }

  @Override
  protected String getSearchUrl() {
    return "/manager/menus/search";
  }

  @Override
  protected String getSearchPlaceHolder() {
    return "search.menus.placeHolder";
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:menu:create";
  }

}
