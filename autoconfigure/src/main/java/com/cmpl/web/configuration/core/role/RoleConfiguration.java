package com.cmpl.web.configuration.core.role;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.role.Privilege;
import com.cmpl.web.core.role.PrivilegeRepository;
import com.cmpl.web.core.role.PrivilegeService;
import com.cmpl.web.core.role.PrivilegeServiceImpl;
import com.cmpl.web.core.role.Role;
import com.cmpl.web.core.role.RoleRepository;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.role.RoleServiceImpl;

@Configuration
@EntityScan(basePackageClasses = {Role.class, Privilege.class})
@EnableJpaRepositories(basePackageClasses = {RoleRepository.class, PrivilegeRepository.class})
public class RoleConfiguration {

  @Bean
  PrivilegeService privilegeService(PrivilegeRepository privilegeRepository) {
    return new PrivilegeServiceImpl(privilegeRepository);
  }

  @Bean
  RoleService roleService(RoleRepository entityRepository, PrivilegeService privilegeService) {
    return new RoleServiceImpl(entityRepository, privilegeService);
  }

  @Bean
  BackMenuItem roleBackMenuItem(BackMenuItem administration, com.cmpl.web.core.common.user.Privilege rolesReadPrivilege) {
    return BackMenuItemBuilder.create().href("back.roles.href").label("back.roles.label").title("back.roles.title")
        .iconClass("fa fa-tasks").parent(administration).order(1).privilege(rolesReadPrivilege.privilege()).build();
  }
}
