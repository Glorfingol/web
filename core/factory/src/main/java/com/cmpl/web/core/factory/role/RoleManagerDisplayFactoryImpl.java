package com.cmpl.web.core.factory.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.common.tree.TreeNode;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.role.PrivilegeDTO;
import com.cmpl.web.core.role.PrivilegeService;
import com.cmpl.web.core.role.RoleCreateForm;
import com.cmpl.web.core.role.RoleDTO;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.role.RoleUpdateForm;

public class RoleManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<RoleDTO> implements
    RoleManagerDisplayFactory {

  private final RoleService roleService;
  private final PrivilegeService privilegeService;
  private final ContextHolder contextHolder;
  private final PluginRegistry<Privilege, String> privileges;

  public RoleManagerDisplayFactoryImpl(RoleService roleService, PrivilegeService privilegeService,
      ContextHolder contextHolder, MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry, PluginRegistry<Privilege, String> privileges) {
    super(menuFactory, messageSource, breadCrumbRegistry);
    this.roleService = roleService;
    this.privilegeService = privilegeService;
    this.contextHolder = contextHolder;
    this.privileges = privileges;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllRoles(Locale locale, int pageNumber) {
    ModelAndView rolesManager = super.computeModelAndViewForBackPage(BACK_PAGE.ROLE_VIEW, locale);
    LOGGER.info("Construction des roles pour la page {} ", BACK_PAGE.ROLE_VIEW.name());

    PageWrapper<RoleDTO> pagedRoleDTOWrapped = computePageWrapper(locale, pageNumber);

    rolesManager.addObject("wrappedRoles", pagedRoleDTOWrapped);

    return rolesManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateRole(Locale locale) {
    ModelAndView roleManager = super.computeModelAndViewForBackPage(BACK_PAGE.ROLE_CREATE, locale);
    LOGGER.info("Construction du formulaire de creation des roles");

    RoleCreateForm form = new RoleCreateForm();

    roleManager.addObject("createForm", form);

    return roleManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateRole(Locale locale, String roleId) {
    ModelAndView roleManager = super.computeModelAndViewForBackPage(BACK_PAGE.ROLE_UPDATE, locale);
    LOGGER.info("Construction du role pour la page {} ", BACK_PAGE.ROLE_UPDATE.name());
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
    LOGGER.info("Construction du role pour la page {} ", BACK_PAGE.ROLE_UPDATE.name());
    RoleDTO role = roleService.getEntity(Long.parseLong(roleId));
    RoleUpdateForm form = new RoleUpdateForm(role);

    roleManager.addObject("updateForm", form);

    List<PrivilegeDTO> privilegesOfRole = privilegeService.findByRoleId(roleId);

    List<Privilege> availablePrivileges = privileges.getPlugins();

    TreeNode<Privilege> rootNode = new TreeNode<>("ALL", messageSource.getI18n("rights:all", locale), null);
    availablePrivileges.stream().forEach(
        p -> {
          TreeNode<Privilege> treeNode = rootNode;
          String[] keyParts = p.privilege().split(":");
          int i = 0;
          String currentKey = null;
          do {
            currentKey = currentKey != null ? currentKey + ":" + keyParts[i] : keyParts[i];
            Optional<TreeNode<Privilege>> child = treeNode.findChildWithId(currentKey);
            if (child.isPresent()) {
              treeNode = child.get();
            } else {
              String labelKey = ("right." + currentKey).replaceAll(":", ".");
              TreeNode<Privilege> newNode = new TreeNode<>(currentKey, messageSource.getMessage(labelKey, null,
                  labelKey, locale));
              if (currentKey.equals(p.privilege())) {
                newNode.setData(p);
              }
              treeNode.addChild(newNode);
              treeNode = newNode;
            }
            i++;
          } while (i <= keyParts.length - 1);

        });

    roleManager.addObject("selectedPrivileges", privilegesOfRole);
    roleManager.addObject("privilegesTree", rootNode);
    return roleManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateRolePrivileges(Locale locale, String roleId) {
    return null;
  }

  @Override
  protected String getBaseUrl() {
    return "manager/roles";
  }

  @Override
  protected Page<RoleDTO> computeEntries(Locale locale, int pageNumber) {
    List<RoleDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(), new Sort(Direction.ASC,
        "name"));
    Page<RoleDTO> pagedRoleDTOEntries = roleService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedRoleDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedRoleDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedRoleDTOEntries.getTotalElements());
  }
}
