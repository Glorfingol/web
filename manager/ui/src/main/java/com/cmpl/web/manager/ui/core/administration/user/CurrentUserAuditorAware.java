package com.cmpl.web.manager.ui.core.administration.user;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.of("system");
    }
    if (authentication.getPrincipal() instanceof String) {
      return Optional.of("user_email");
    }
    return Optional.of(((CurrentUser) authentication.getPrincipal()).getUsername());
  }
}
