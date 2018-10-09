package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.service.DefaultBaseService;
import com.cmpl.web.core.models.Responsibility;
import java.util.List;

public class DefaultResponsibilityService extends
  DefaultBaseService<ResponsibilityDTO, Responsibility>
  implements ResponsibilityService {

  private final ResponsibilityDAO responsibilityDAO;

  public DefaultResponsibilityService(ResponsibilityDAO responsibilityDAO,
    ResponsibilityMapper responsibilityMapper) {
    super(responsibilityDAO, responsibilityMapper);
    this.responsibilityDAO = responsibilityDAO;
  }

  @Override
  public List<ResponsibilityDTO> findByUserId(Long userId) {
    return mapper.toListDTO(responsibilityDAO.findByUserId(userId));
  }

  @Override
  public List<ResponsibilityDTO> findByRoleId(Long roleId) {
    return mapper.toListDTO(responsibilityDAO.findByRoleId(roleId));
  }

  @Override
  public ResponsibilityDTO findByUserIdAndRoleId(Long userId, Long roleId) {
    return mapper.toDTO(responsibilityDAO.findByUserIdAndRoleId(userId, roleId));
  }

}
