package com.cmpl.web.core.group;

import com.cmpl.web.core.models.BOGroup;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {BOGroup.class})
@EnableJpaRepositories(basePackageClasses = {GroupRepository.class})
public class GroupConfiguration {

  @Bean
  public GroupMapper groupMapper() {
    return new GroupMapper();
  }

  @Bean
  public GroupDAO groupDAO(ApplicationEventPublisher publisher, GroupRepository groupRepository) {
    return new DefaultGroupDAO(groupRepository, publisher);
  }

  @Bean
  public GroupService groupService(GroupDAO groupDAO, GroupMapper groupMapper) {
    return new DefaultGroupService(groupDAO, groupMapper);
  }

  @Bean
  public GroupTranslator groupTranslator() {
    return new DefaultGroupTranslator();
  }

  @Bean
  public GroupDispatcher groupDispatcher(GroupService groupService,
      GroupTranslator groupTranslator) {
    return new DefaultGroupDispatcher(groupTranslator, groupService);
  }

}
