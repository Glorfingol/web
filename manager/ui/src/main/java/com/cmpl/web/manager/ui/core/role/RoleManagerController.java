package com.cmpl.web.manager.ui.core.role;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.role.RoleManagerDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.role.RoleCreateForm;
import com.cmpl.web.core.role.RoleDispatcher;
import com.cmpl.web.core.role.RoleResponse;
import com.cmpl.web.core.role.RoleUpdateForm;
import com.cmpl.web.manager.ui.core.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager/roles")
public class RoleManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RoleManagerController.class);

  private final RoleDispatcher roleDispatcher;
  private final RoleManagerDisplayFactory roleManagerDisplayFactory;

  public RoleManagerController(RoleDispatcher roleDispatcher, RoleManagerDisplayFactory roleManagerDisplayFactory) {
    this.roleDispatcher = roleDispatcher;
    this.roleManagerDisplayFactory = roleManagerDisplayFactory;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('administration:roles:read')")
  public ModelAndView printViewRoles(@RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_VIEW.name());
    return roleManagerDisplayFactory.computeModelAndViewForViewAllRoles(locale, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  @PreAuthorize("hasAuthority('administration:roles:create')")
  public ModelAndView printCreateRole(Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_CREATE.name());
    return roleManagerDisplayFactory.computeModelAndViewForCreateRole(locale);
  }

  @PostMapping(produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:roles:create')")
  public ResponseEntity<RoleResponse> createRole(@RequestBody RoleCreateForm createForm, Locale locale) {
    LOGGER.info("Tentative de création d'un role");
    try {
      RoleResponse response = roleDispatcher.createEntity(createForm, locale);
      if (response.getRole() != null) {
        LOGGER.info("Entrée crée, id " + response.getRole().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @PutMapping(value = "/{roleId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:roles:write')")
  public ResponseEntity<RoleResponse> updateRole(@RequestBody RoleUpdateForm updateForm, Locale locale) {

    LOGGER.info("Tentative de modification d'un role");
    try {
      RoleResponse response = roleDispatcher.updateEntity(updateForm, locale);
      if (response.getRole() != null) {
        LOGGER.info("Entrée modifiée, id " + response.getRole().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @GetMapping(value = "/{roleId}")
  @PreAuthorize("hasAuthority('administration:roles:read')")
  public ModelAndView printViewUpdateRole(@PathVariable(value = "roleId") String roleId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_UPDATE.name() + " pour " + roleId);
    return roleManagerDisplayFactory.computeModelAndViewForUpdateRole(locale, roleId);
  }

  @GetMapping(value = "/{roleId}/_main")
  @PreAuthorize("hasAuthority('administration:roles:read')")
  public ModelAndView printViewUpdateRoleMain(@PathVariable(value = "roleId") String roleId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_UPDATE.name() + " pour " + roleId + " pour la partie main");
    return roleManagerDisplayFactory.computeModelAndViewForUpdateRoleMain(locale, roleId);
  }

  @GetMapping(value = "/{roleId}/_privileges")
  @PreAuthorize("hasAuthority('administration:roles:read')")
  public ModelAndView printViewUpdateRolePrivileges(@PathVariable(value = "roleId") String roleId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_UPDATE.name() + " pour " + roleId + " pour la partie privileges");
    return roleManagerDisplayFactory.computeModelAndViewForUpdateRolePrivileges(locale, roleId);
  }

}
