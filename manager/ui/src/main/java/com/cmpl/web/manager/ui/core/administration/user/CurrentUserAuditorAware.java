package com.cmpl.web.manager.ui.core.administration.user;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserAuditorAware implements AuditorAware<CurrentUser> {

  @Override
  public Optional<CurrentUser> getCurrentAuditor() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.empty();
    }
    return Optional.of(((CurrentUser) authentication.getPrincipal()));
  }
}
