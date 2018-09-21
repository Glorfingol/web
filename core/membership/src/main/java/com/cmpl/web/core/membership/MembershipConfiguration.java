package com.cmpl.web.core.membership;

import com.cmpl.web.core.models.Membership;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {Membership.class})
@EnableJpaRepositories(basePackageClasses = {MembershipRepository.class})
public class MembershipConfiguration {

  @Bean
  public MembershipDAO membershipDAO(MembershipRepository membershipRepository,
      ApplicationEventPublisher publisher) {
    return new DefaultMembershipDAO(membershipRepository, publisher);
  }

  @Bean
  public MembershipMapper membershipMapper() {
    return new MembershipMapper();
  }

  @Bean
  public MembershipService membershipService(MembershipDAO membershipDAO,
      MembershipMapper associationEntityGroupMapper) {
    return new DefaultMembershipService(membershipDAO, associationEntityGroupMapper);
  }

  @Bean
  public MembershipTranslator membershipTranslator() {
    return new DefaultMembershipTranslator();
  }

  @Bean
  public MembershipDispatcher membershipDispatcher(MembershipService membershipService,
      MembershipTranslator membershipTranslator) {
    return new DefaultMembershipDispatcher(membershipService, membershipTranslator);
  }

}
