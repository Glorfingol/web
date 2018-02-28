package com.cmpl.web.core.user;

import java.time.LocalDateTime;

import com.cmpl.web.core.common.service.BaseService;

public interface UserService extends BaseService<UserDTO> {

  UserDTO findByLogin(String login);

  UserDTO updateLastConnection(Long userId, LocalDateTime connectionDateTime);
}