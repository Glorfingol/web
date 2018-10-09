package com.cmpl.web.core.factory.user;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.responsibility.ResponsibilityCreateFormBuilder;
import com.cmpl.web.core.responsibility.ResponsibilityService;
import com.cmpl.web.core.role.RoleDTO;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.user.UserCreateForm;
import com.cmpl.web.core.user.UserDTO;
import com.cmpl.web.core.user.UserService;
import com.cmpl.web.core.user.UserUpdateForm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public class DefaultUserManagerDisplayFactory extends AbstractBackDisplayFactory<UserDTO>
  implements UserManagerDisplayFactory {

  private final UserService userService;

  private final RoleService roleService;

  private final ResponsibilityService responsibilityService;

  private static final String LINKABLE_ROLES = "linkableRoles";

  private static final String LINKED_ROLES = "linkedRoles";


  public DefaultUserManagerDisplayFactory(UserService userService, RoleService roleService,
    ResponsibilityService responsibilityService, ContextHolder contextHolder,
    MenuFactory menuFactory,
    WebMessageSource messageSource, PluginRegistry<BreadCrumb, String> breadCrumbRegistry,
    Set<Locale> availableLocales, GroupService groupService, MembershipService membershipService,
    PluginRegistry<BackPage, String> backPagesRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService,
      membershipService,
      backPagesRegistry, contextHolder);
    this.userService = Objects.requireNonNull(userService);

    this.roleService = Objects.requireNonNull(roleService);

    this.responsibilityService = Objects.requireNonNull(responsibilityService);


  }

  @Override
  public ModelAndView computeModelAndViewForViewAllUsers(Locale locale, int pageNumber) {
    BackPage backPage = computeBackPage("USER_VIEW");
    ModelAndView usersManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des users pour la page {} ", backPage);

    PageWrapper<UserDTO> pagedUserDTOWrapped = computePageWrapper(locale, pageNumber, "");

    usersManager.addObject("wrappedEntities", pagedUserDTOWrapped);

    return usersManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateUser(Locale locale) {
    BackPage backPage = computeBackPage("USER_CREATE");
    ModelAndView userManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du formulaire de creation des users");

    UserCreateForm form = new UserCreateForm();

    userManager.addObject("createForm", form);

    return userManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateUser(Locale locale, String userId) {
    BackPage backPage = computeBackPage("USER_UPDATE");
    ModelAndView userManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du user pour la page {} ", backPage.getPageName());
    UserDTO user = userService.getEntity(Long.parseLong(userId));
    UserUpdateForm form = new UserUpdateForm(user);

    userManager.addObject("updateForm", form);

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(user.getLogin()).build();
    BreadCrumb breadCrumb = (BreadCrumb) userManager.getModel().get("breadcrumb");
    if (canAddBreadCrumbItem(breadCrumb, item)) {
      breadCrumb.getItems().add(item);
    }

    return userManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateUserMain(Locale locale, String userId) {
    ModelAndView userManager = new ModelAndView("back/users/edit/tab_main");
    UserDTO user = userService.getEntity(Long.parseLong(userId));
    UserUpdateForm form = new UserUpdateForm(user);

    userManager.addObject("updateForm", form);
    return userManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateUserRoles(Locale locale, String userId) {
    ModelAndView userManager = new ModelAndView("back/users/edit/tab_roles");

    List<RoleDTO> associatedRoles = responsibilityService.findByUserId(Long.parseLong(userId))
      .stream()
      .map(association -> roleService.getEntity(association.getRoleId()))
      .collect(Collectors.toList());

    List<RoleDTO> linkableRoles = roleService.getEntities().stream()
      .filter(role -> !associatedRoles.stream()
        .filter(associatedRole -> associatedRole.getId().equals(role.getId()))
        .map(filteredRole -> filteredRole.getId()).collect(Collectors.toList())
        .contains(role.getId()))
      .collect(Collectors.toList());

    Collections.sort(associatedRoles, Comparator.comparing(RoleDTO::getName));
    userManager.addObject("linkedRoles", associatedRoles);
    Collections.sort(linkableRoles, Comparator.comparing(RoleDTO::getName));
    userManager.addObject("linkableRoles", linkableRoles);
    userManager
      .addObject("createForm", ResponsibilityCreateFormBuilder.create().userId(userId).build());
    return userManager;
  }

  @Override
  public ModelAndView computeLinkedRoles(String userId, String query) {
    PageRequest pageRequest = PageRequest.of(0, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<RoleDTO> linkedRoles = roleService
      .searchLinkedEntities(pageRequest, query, Long.parseLong(userId));
    ModelAndView websiteManager = new ModelAndView("back/users/edit/linked_roles");
    websiteManager.addObject(LINKED_ROLES, linkedRoles);
    return websiteManager;
  }

  @Override
  public ModelAndView computeLinkableRoles(String userId, String query) {
    PageRequest pageRequest = PageRequest.of(0, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<RoleDTO> linkableRoles = roleService
      .searchNotLinkedEntities(pageRequest, query, Long.parseLong(userId));
    ModelAndView websiteManager = new ModelAndView("back/users/edit/linkable_roles");
    websiteManager.addObject(LINKABLE_ROLES, linkableRoles);
    return websiteManager;
  }

  @Override
  protected Page<UserDTO> computeEntries(Locale locale, int pageNumber, String query) {
    List<UserDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "login"));
    Page<UserDTO> pagedUserDTOEntries;
    if (StringUtils.hasText(query)) {
      pagedUserDTOEntries = userService.searchEntities(pageRequest, query);
    } else {
      pagedUserDTOEntries = userService.getPagedEntities(pageRequest);
    }
    if (CollectionUtils.isEmpty(pagedUserDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedUserDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedUserDTOEntries.getTotalElements());
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/users";
  }

  @Override
  protected String getItemLink() {
    return "/manager/users/";
  }

  @Override
  protected String getSearchUrl() {
    return "/manager/users/search";
  }

  @Override
  protected String getSearchPlaceHolder() {
    return "search.users.placeHolder";
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "administration:users:create";
  }
}
