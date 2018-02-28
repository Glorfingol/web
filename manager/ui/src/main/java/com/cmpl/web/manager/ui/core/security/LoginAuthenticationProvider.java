package com.cmpl.web.manager.ui.core.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class LoginAuthenticationProvider extends DaoAuthenticationProvider {

  public LoginAuthenticationProvider(UserDetailsService userDetailsService) {
    super.setUserDetailsService(userDetailsService);
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Object detailsObject = authentication.getDetails();
    if (detailsObject instanceof WebAuthenticationDetails) {
      final WebAuthenticationDetails details = (WebAuthenticationDetails) detailsObject;

      try {
        Authentication auth = super.authenticate(authentication);
        return auth;
      } catch (BadCredentialsException e) {
        throw e;
      }
    }
    return super.authenticate(authentication);
  }

}
