package com.cmpl.web.core.factory.role;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.factory.AbstractBackDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.role.RoleCreateForm;
import com.cmpl.web.core.role.RoleDTO;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.role.RoleUpdateForm;
import com.cmpl.web.core.role.privilege.PrivilegeDTO;
import com.cmpl.web.core.role.privilege.PrivilegeService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
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

public class DefaultRoleManagerDisplayFactory extends AbstractBackDisplayFactory<RoleDTO>
  implements RoleManagerDisplayFactory {

  private final RoleService roleService;

  private final PrivilegeService privilegeService;

  private final PluginRegistry<Privilege, String> privileges;

  public DefaultRoleManagerDisplayFactory(RoleService roleService,
    PrivilegeService privilegeService,
    ContextHolder contextHolder, MenuFactory menuFactory, WebMessageSource messageSource,
    PluginRegistry<BreadCrumb, String> breadCrumbRegistry,
    PluginRegistry<Privilege, String> privileges,
    Set<Locale> availableLocales, GroupService groupService,
    MembershipService membershipService, PluginRegistry<BackPage, String> backPagesRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService,
      membershipService, backPagesRegistry, contextHolder);
    this.roleService = Objects.requireNonNull(roleService);

    this.privilegeService = Objects.requireNonNull(privilegeService);

    this.privileges = Objects.requireNonNull(privileges);

  }

  @Override
  public ModelAndView computeModelAndViewForViewAllRoles(Locale locale, int pageNumber) {
    BackPage backPage = computeBackPage("ROLE_VIEW");
    ModelAndView rolesManager = super
      .computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des roles pour la page {} ", backPage.getPageName());

    PageWrapper<RoleDTO> pagedRoleDTOWrapped = computePageWrapper(locale, pageNumber, "");

    rolesManager.addObject("wrappedEntities", pagedRoleDTOWrapped);

    return rolesManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateRole(Locale locale) {
    BackPage backPage = computeBackPage("ROLE_CREATE");
    ModelAndView roleManager = super
      .computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du formulaire de creation des roles");

    RoleCreateForm form = new RoleCreateForm();

    roleManager.addObject("createForm", form);

    return roleManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateRole(Locale locale, String roleId) {
    BackPage backPage = computeBackPage("ROLE_UPDATE");
    ModelAndView roleManager = super
      .computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du role pour la page {} ", backPage.getPageName());
    RoleDTO role = roleService.getEntity(Long.parseLong(roleId));
    RoleUpdateForm form = new RoleUpdateForm(role);

    roleManager.addObject("updateForm", form);

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(role.getName()).build();
    BreadCrumb breadCrumb = (BreadCrumb) roleManager.getModel().get("breadcrumb");
    if (canAddBreadCrumbItem(breadCrumb, item)) {
      breadCrumb.getItems().add(item);
    }

    return roleManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateRoleMain(Locale locale, String roleId) {
    ModelAndView roleManager = new ModelAndView("back/roles/edit/tab_main");
    RoleDTO role = roleService.getEntity(Long.parseLong(roleId));
    RoleUpdateForm form = new RoleUpdateForm(role);

    roleManager.addObject("updateForm", form);

    return roleManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateRolePrivileges(Locale locale, String roleId) {
    ModelAndView roleManager = new ModelAndView("back/roles/edit/tab_privileges");

    RoleDTO role = roleService.getEntity(Long.parseLong(roleId));
    RoleUpdateForm form = new RoleUpdateForm(role);

    roleManager.addObject("updateForm", form);

    List<PrivilegeDTO> privilegesOfRole = privilegeService.findByRoleId(Long.parseLong(roleId));

    List<Privilege> availablePrivileges = privileges.getPlugins();

    roleManager.addObject("selectedPrivileges", privilegesOfRole);

    Map<String, Map<String, Map<String, Boolean>>> privileges = new HashMap<>();
    boolean isAllSelected = availablePrivileges.size() == privilegesOfRole.size();
    privileges.put("all", prepareAllRightsFeature(isAllSelected));

    availablePrivileges.forEach(privilege -> {
      Map<String, Map<String, Boolean>> namespacePrivileges = privileges.get(privilege.namespace());
      if (namespacePrivileges == null) {
        namespacePrivileges = new TreeMap<>();
        privileges.put(privilege.namespace(), namespacePrivileges);
      }
      Map<String, Boolean> featurePrivileges = namespacePrivileges.get(privilege.feature());
      if (featurePrivileges == null) {
        featurePrivileges = new TreeMap<>();
        namespacePrivileges.put(privilege.feature(), featurePrivileges);
      }

      featurePrivileges.put(privilege.right(),
        !privilegesOfRole.stream()
          .filter(privilegeOfRole -> privilegeOfRole.getContent().equals(privilege.privilege()))
          .collect(Collectors.toList()).isEmpty());
    });
    roleManager.addObject("privilegesTree", privileges);
    return roleManager;
  }

  private Map<String, Map<String, Boolean>> prepareAllRightsFeature(boolean isAllSelected) {
    Map<String, Map<String, Boolean>> allFeature = new HashMap<>();
    Map<String, Boolean> allRight = new HashMap<>();
    allRight.put("all", isAllSelected);
    allFeature.put("all", allRight);
    return allFeature;
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/roles";
  }

  @Override
  protected String getItemLink() {
    return "/manager/roles/";
  }

  @Override
  protected String getSearchUrl() {
    return "/manager/roles/search";
  }


  @Override
  protected String getSearchPlaceHolder() {
    return "search.roles.placeHolder";
  }

  @Override
  protected Page<RoleDTO> computeEntries(Locale locale, int pageNumber, String query) {
    List<RoleDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<RoleDTO> pagedRoleDTOEntries;
    if (StringUtils.hasText(query)) {
      pagedRoleDTOEntries = roleService.searchEntities(pageRequest, query);
    } else {
      pagedRoleDTOEntries = roleService.getPagedEntities(pageRequest);
    }
    if (CollectionUtils.isEmpty(pagedRoleDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedRoleDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedRoleDTOEntries.getTotalElements());
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "administration:roles:create";
  }
}
