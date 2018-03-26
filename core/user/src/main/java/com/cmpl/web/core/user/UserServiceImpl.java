package com.cmpl.web.core.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import com.cmpl.web.core.common.service.BaseServiceImpl;

@CacheConfig(cacheNames = "users")
public class UserServiceImpl extends BaseServiceImpl<UserDTO, User> implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    super(userRepository);
    this.userRepository = userRepository;
  }

  @Override
  @CachePut(key = "#a0.id")
  public UserDTO updateEntity(UserDTO dto) {
    UserDTO updatedUser = super.updateEntity(dto);
    return updatedUser;
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
  @Cacheable(value = "pagedUsers")
  public Page<UserDTO> getPagedEntities(PageRequest pageRequest) {
    return super.getPagedEntities(pageRequest);
  }

  @Override
  @Cacheable(value = "listedUsers")
  public List<UserDTO> getUsers() {
    return toListDTO(userRepository.findAll(new Sort(Direction.ASC, "login")));
  }

  @Override
  @Cacheable(key = "#a0")
  public UserDTO getEntity(Long userId) {
    UserDTO fetchedUser = super.getEntity(userId);

    return fetchedUser;
  }

  @Override
  @Transactional
  @CacheEvict(value = {"pagedUsers", "listedUsers"}, allEntries = true)
  public UserDTO createEntity(UserDTO dto) {
    UserDTO createdUser = super.createEntity(dto);

    return createdUser;

  }

  @Override
  @CacheEvict(value = {"pagedUsers", "listedUsers"}, allEntries = true)
  @CachePut(key = "#a0")
  public UserDTO updateLastConnection(Long userId, LocalDateTime connectionDateTime) {
    Optional<User> result = userRepository.findById(userId);
    if (!result.isPresent()) {
      return null;
    }
    UserDTO user = toDTO(result.get());
    user.setLastConnection(connectionDateTime);
    user = toDTO(userRepository.save(toEntity(user)));

    return user;

  }
}
