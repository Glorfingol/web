package com.cmpl.web.core.role;

import com.cmpl.web.core.models.Privilege;
import com.cmpl.web.core.models.Role;
import com.cmpl.web.core.role.privilege.DefaultPrivilegeDAO;
import com.cmpl.web.core.role.privilege.DefaultPrivilegeService;
import com.cmpl.web.core.role.privilege.PrivilegeDAO;
import com.cmpl.web.core.role.privilege.PrivilegeMapper;
import com.cmpl.web.core.role.privilege.PrivilegeRepository;
import com.cmpl.web.core.role.privilege.PrivilegeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.PluginRegistry;

@Configuration
@EntityScan(basePackageClasses = {Role.class, Privilege.class})
@EnableJpaRepositories(basePackageClasses = {RoleRepository.class, PrivilegeRepository.class})
public class RoleConfiguration {

  @Bean
  public PrivilegeMapper privilegeMapper() {
    return new PrivilegeMapper();
  }

  @Bean
  public PrivilegeDAO privilegeDAO(ApplicationEventPublisher publisher,
      PrivilegeRepository privilegeRepository) {
    return new DefaultPrivilegeDAO(privilegeRepository, publisher);
  }

  @Bean
  public PrivilegeService privilegeService(PrivilegeDAO privilegeDAO,
      PrivilegeMapper privilegeMapper) {
    return new DefaultPrivilegeService(privilegeDAO, privilegeMapper);
  }

  @Bean
  public RoleMapper roleMapper(PrivilegeService privilegeService) {
    return new RoleMapper(privilegeService);
  }

  @Bean
  public RoleDAO roleDAO(ApplicationEventPublisher publisher, RoleRepository roleRepository) {
    return new DefaultRoleDAO(roleRepository, publisher);
  }

  @Bean
  public RoleService roleService(RoleDAO roleDAO, RoleMapper roleMapper) {
    return new DefaultRoleService(roleDAO, roleMapper);
  }

  @Bean
  public RoleTranslator roleTranslator() {
    return new DefaultRoleTranslator();
  }

  @Bean
  public RoleDispatcher roleDispatcher(RoleService roleService, PrivilegeService privilegeService,
      RoleTranslator roleTranslator,
      @Qualifier(value = "privileges") PluginRegistry<com.cmpl.web.core.common.user.Privilege, String> privileges) {
    return new DefaultRoleDispatcher(roleService, privilegeService, roleTranslator, privileges);
  }

}
