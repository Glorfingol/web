package com.cmpl.web.core.user;

import java.time.LocalDateTime;

import com.cmpl.web.core.common.service.BaseServiceImpl;

public class UserServiceImpl extends BaseServiceImpl<UserDTO, User> implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    super(userRepository);
    this.userRepository = userRepository;
  }

  @Override
  protected UserDTO toDTO(User entity) {
    UserDTO dto = UserDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected User toEntity(UserDTO dto) {
    User entity = UserBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }

  @Override
  public UserDTO findByLogin(String login) {
    User user = userRepository.findByLogin(login);
    if (user == null) {
      return null;
    }
    return toDTO(user);
  }

  @Override
  public UserDTO updateLastConnection(Long userId, LocalDateTime connectionDateTime) {
    UserDTO user = toDTO(userRepository.getOne(userId));
    user.setLastConnection(connectionDateTime);
    user = toDTO(userRepository.save(toEntity(user)));

    return user;

  }
}
