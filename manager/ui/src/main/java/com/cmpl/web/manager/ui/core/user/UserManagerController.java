package com.cmpl.web.manager.ui.core.user;

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

import com.cmpl.web.core.factory.user.UserManagerDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.user.UserCreateForm;
import com.cmpl.web.core.user.UserDispatcher;
import com.cmpl.web.core.user.UserResponse;
import com.cmpl.web.core.user.UserUpdateForm;
import com.cmpl.web.manager.ui.core.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager/users")
public class UserManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserManagerController.class);

  private final UserManagerDisplayFactory userManagerDisplayFactory;
  private final UserDispatcher userDispatcher;

  public UserManagerController(UserManagerDisplayFactory userManagerDisplayFactory, UserDispatcher userDispatcher) {
    this.userManagerDisplayFactory = userManagerDisplayFactory;
    this.userDispatcher = userDispatcher;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('administration:users:read')")
  public ModelAndView printViewUsers(@RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_VIEW.name());
    return userManagerDisplayFactory.computeModelAndViewForViewAllUsers(locale, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  @PreAuthorize("hasAuthority('administration:users:create')")
  public ModelAndView printCreateUser(Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_CREATE.name());
    return userManagerDisplayFactory.computeModelAndViewForCreateUser(locale);
  }

  @PostMapping(produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:users:create')")
  public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateForm createForm, Locale locale) {
    LOGGER.info("Tentative de création d'une page");
    try {
      UserResponse response = userDispatcher.createEntity(createForm, locale);
      if (response.getUser() != null) {
        LOGGER.info("Entrée crée, id " + response.getUser().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @PutMapping(value = "/{userId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:users:write')")
  public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateForm updateForm, Locale locale) {

    LOGGER.info("Tentative de modification d'une page");
    try {
      UserResponse response = userDispatcher.updateEntity(updateForm, locale);
      if (response.getUser() != null) {
        LOGGER.info("Entrée modifiée, id " + response.getUser().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @GetMapping(value = "/{userId}")
  @PreAuthorize("hasAuthority('administration:users:read')")
  public ModelAndView printViewUpdateWidget(@PathVariable(value = "userId") String userId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_UPDATE.name() + " pour " + userId);
    return userManagerDisplayFactory.computeModelAndViewForUpdateUser(locale, userId);
  }

  @GetMapping(value = "/{userId}/_main")
  @PreAuthorize("hasAuthority('administration:users:read')")
  public ModelAndView printViewUpdateUserMain(@PathVariable(value = "userId") String userId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_UPDATE.name() + " pour " + userId + " pour la partie main");
    return userManagerDisplayFactory.computeModelAndViewForUpdateUserMain(locale, userId);
  }

  @GetMapping(value = "/{userId}/_roles")
  @PreAuthorize("hasAuthority('administration:users:read')")
  public ModelAndView printViewUpdateUserRoles(@PathVariable(value = "userId") String userId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_UPDATE.name() + " pour " + userId + " pour la partie roles");
    return userManagerDisplayFactory.computeModelAndViewForUpdateUserRoles(locale, userId);
  }

}
