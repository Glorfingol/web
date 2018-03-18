package com.cmpl.web.core.user;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;

public class UserDispatcherImpl implements UserDispatcher {

  private final UserValidator validator;
  private final UserTranslator translator;
  private final UserService service;

  public UserDispatcherImpl(UserValidator validator, UserTranslator translator, UserService userService) {
    this.validator = validator;
    this.translator = translator;
    this.service = userService;
  }

  @Override
  public UserResponse createEntity(UserCreateForm form, Locale locale) {

    Error error = validator.validateCreate(form, locale);

    if (error != null) {
      return UserResponseBuilder.create().error(error).build();
    }

    UserDTO userToCreate = translator.fromCreateFormToDTO(form);
    UserDTO createdUser = service.createEntity(userToCreate);
    return translator.fromDTOToResponse(createdUser);

  }

  @Override
  public UserResponse updateEntity(UserUpdateForm form, Locale locale) {
    Error error = validator.validateUpdate(form, locale);

    if (error != null) {
      return UserResponseBuilder.create().error(error).build();
    }

    UserDTO userToUpdate = service.getEntity(form.getId());
    userToUpdate.setDescription(form.getDescription());
    userToUpdate.setEmail(form.getEmail());
    userToUpdate.setLastConnection(form.getLastConnection());
    userToUpdate.setLogin(form.getLogin());
    userToUpdate.setPassword(form.getPassword());

    UserDTO userUpdated = service.updateEntity(userToUpdate);
    return translator.fromDTOToResponse(userUpdated);
  }
}
