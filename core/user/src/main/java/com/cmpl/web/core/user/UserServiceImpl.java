package com.cmpl.web.core.user;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.common.service.BaseServiceImpl;

public class UserServiceImpl extends BaseServiceImpl<UserDTO, User> implements UserService {

  public UserServiceImpl(BaseRepository<User> entityRepository) {
    super(entityRepository);
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
}
