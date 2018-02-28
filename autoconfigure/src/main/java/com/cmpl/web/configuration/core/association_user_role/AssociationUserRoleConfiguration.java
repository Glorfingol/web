package com.cmpl.web.configuration.core.association_user_role;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.association_user_role.AssociationUserRole;
import com.cmpl.web.core.association_user_role.AssociationUserRoleRepository;
import com.cmpl.web.core.association_user_role.AssociationUserRoleService;
import com.cmpl.web.core.association_user_role.AssociationUserRoleServiceImpl;

@Configuration
@EntityScan(basePackageClasses = AssociationUserRole.class)
@EnableJpaRepositories(basePackageClasses = AssociationUserRoleRepository.class)
public class AssociationUserRoleConfiguration {

  @Bean
  AssociationUserRoleService associationUserRoleService(AssociationUserRoleRepository associationUserRoleRepository) {
    return new AssociationUserRoleServiceImpl(associationUserRoleRepository);
  }

}
