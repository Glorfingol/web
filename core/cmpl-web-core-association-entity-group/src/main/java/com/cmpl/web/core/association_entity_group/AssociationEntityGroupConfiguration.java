package com.cmpl.web.core.association_entity_group;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.models.AssociationEntityGroup;

@Configuration
@EntityScan(basePackageClasses = {AssociationEntityGroup.class})
@EnableJpaRepositories(basePackageClasses = {AssociationEntityGroupRepository.class})
public class AssociationEntityGroupConfiguration {

  @Bean
  public AssociationEntityGroupDAO associationEntityGroupDAO(
      AssociationEntityGroupRepository associationEntityGroupRepository, ApplicationEventPublisher publisher) {
    return new AssociationEntityGroupDAOImpl(associationEntityGroupRepository, publisher);
  }

  @Bean
  public AssociationEntityGroupMapper associationEntityGroupMapper() {
    return new AssociationEntityGroupMapper();
  }

  @Bean
  public AssociationEntityGroupService associationEntityGroupService(
      AssociationEntityGroupDAO associationEntityGroupDAO, AssociationEntityGroupMapper associationEntityGroupMapper) {
    return new AssociationEntityGroupServiceImpl(associationEntityGroupDAO, associationEntityGroupMapper);
  }

}
