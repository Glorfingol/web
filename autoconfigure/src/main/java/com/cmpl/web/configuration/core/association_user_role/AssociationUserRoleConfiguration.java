package com.cmpl.web.configuration.core.association_user_role;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.association_user_role.AssociationUserRole;
import com.cmpl.web.core.association_user_role.AssociationUserRoleDAO;
import com.cmpl.web.core.association_user_role.AssociationUserRoleDAOImpl;
import com.cmpl.web.core.association_user_role.AssociationUserRoleDispatcher;
import com.cmpl.web.core.association_user_role.AssociationUserRoleDispatcherImpl;
import com.cmpl.web.core.association_user_role.AssociationUserRoleMapper;
import com.cmpl.web.core.association_user_role.AssociationUserRoleRepository;
import com.cmpl.web.core.association_user_role.AssociationUserRoleService;
import com.cmpl.web.core.association_user_role.AssociationUserRoleServiceImpl;
import com.cmpl.web.core.association_user_role.AssociationUserRoleTranslator;
import com.cmpl.web.core.association_user_role.AssociationUserRoleTranslatorImpl;

@Configuration
@EntityScan(basePackageClasses = AssociationUserRole.class)
@EnableJpaRepositories(basePackageClasses = AssociationUserRoleRepository.class)
public class AssociationUserRoleConfiguration {

  @Bean
  public AssociationUserRoleDAO associationUserRoleDAO(AssociationUserRoleRepository associationUserRoleRepository,
      ApplicationEventPublisher publisher) {
    return new AssociationUserRoleDAOImpl(associationUserRoleRepository, publisher);
  }

  @Bean
  public AssociationUserRoleMapper associationUserRoleMapper() {
    return new AssociationUserRoleMapper();
  }

  @Bean
  public AssociationUserRoleService associationUserRoleService(AssociationUserRoleDAO associationUserRoleDAO,
      AssociationUserRoleMapper associationUserRoleMapper) {
    return new AssociationUserRoleServiceImpl(associationUserRoleDAO, associationUserRoleMapper);
  }

  @Bean
  public AssociationUserRoleTranslator associationUserRoleTranslator() {
    return new AssociationUserRoleTranslatorImpl();
  }

  @Bean
  public AssociationUserRoleDispatcher associationUserRoleDispatcher(
      AssociationUserRoleService associationUserRoleService,
      AssociationUserRoleTranslator associationUserRoleTranslator) {
    return new AssociationUserRoleDispatcherImpl(associationUserRoleService, associationUserRoleTranslator);
  }

}
