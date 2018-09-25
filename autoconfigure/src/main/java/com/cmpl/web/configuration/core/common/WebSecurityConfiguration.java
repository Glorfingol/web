package com.cmpl.web.configuration.core.common;

import com.cmpl.web.core.common.user.ActionTokenService;
import com.cmpl.web.core.common.user.DefaultActionTokenService;
import com.cmpl.web.core.user.UserService;
import com.cmpl.web.manager.ui.core.administration.user.DefaultLastConnectionUpdateAuthenticationSuccessHandler;
import com.cmpl.web.manager.ui.core.common.security.AuthenticationFailureListener;
import com.cmpl.web.manager.ui.core.common.security.AuthenticationSuccessListener;
import com.cmpl.web.manager.ui.core.common.security.DefaultLoginAttemptsService;
import com.cmpl.web.manager.ui.core.common.security.LoginAttemptsService;
import com.cmpl.web.manager.ui.core.common.security.LoginAuthenticationProvider;
import com.cmpl.web.manager.ui.core.common.security.PasswordTooOldInterceptor;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration de la securite
 *
 * @author Louis
 */
@Configuration
@EnableWebSecurity
@PropertySource("classpath:/core/core.properties")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfiguration {


  @Value("${reset.token.secret.string}")
  String secretString;

  @Value("${reset.token.secret.integer}")
  Integer secretInteger;

  @Bean
  @ConditionalOnMissingBean(TokenService.class)
  public TokenService tokenService() {
    KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();
    tokenService.setServerInteger(secretInteger);
    tokenService.setServerSecret(secretString);
    tokenService.setSecureRandom(secureRandom());
    return tokenService;
  }

  @Bean
  public LoginAttemptsService loginAttemptsService() {
    return new DefaultLoginAttemptsService(10);
  }

  @Bean
  public ActionTokenService actionTokenService(TokenService tokenService) {
    return new DefaultActionTokenService(tokenService);
  }

  @Bean
  public AuthenticationFailureListener authenticationFailureListener(
    LoginAttemptsService loginAttemptService) {
    return new AuthenticationFailureListener(loginAttemptService);
  }

  @Bean
  public PasswordTooOldInterceptor passwordTooOldInterceptor(UserService userService) {
    return new PasswordTooOldInterceptor(userService);
  }

  @Bean
  public AuthenticationSuccessListener authenticationSuccessListener(
    LoginAttemptsService loginAttemptService) {
    return new AuthenticationSuccessListener(loginAttemptService);
  }

  @Bean
  public LoginAuthenticationProvider loginAuthenticationProvider(
    UserDetailsService dbUserDetailsService,
    PasswordEncoder passwordEncoder, LoginAttemptsService userLoginAttemptsService) {

    LoginAuthenticationProvider provider = new LoginAuthenticationProvider(dbUserDetailsService,
      userLoginAttemptsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
  }

  @Bean
  @ConditionalOnMissingBean(PasswordEncoder.class)
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10, secureRandom());
  }

  @Bean
  @ConditionalOnMissingBean(SecureRandom.class)
  public SecureRandom secureRandom() {
    try {
      return SecureRandom.getInstance("SHA1PRNG");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Can't find the SHA1PRNG algorithm for generating random numbers",
        e);
    }
  }

  @Configuration
  public static class LoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    public final LoginAuthenticationProvider loginAuthenticationProvider;

    private final DefaultLastConnectionUpdateAuthenticationSuccessHandler lastConnectionUpdateAuthenticationSuccessHandler;

    public LoginWebSecurityConfigurerAdapter(
      LoginAuthenticationProvider loginAuthenticationProvider,
      DefaultLastConnectionUpdateAuthenticationSuccessHandler lastConnectionUpdateAuthenticationSuccessHandler) {
      this.loginAuthenticationProvider = loginAuthenticationProvider;
      this.lastConnectionUpdateAuthenticationSuccessHandler = lastConnectionUpdateAuthenticationSuccessHandler;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      String[] authorizedUrls = prepareAuthorizedUrls();
      http.headers().frameOptions().sameOrigin().and().authorizeRequests()
        .antMatchers(authorizedUrls).permitAll()
        .anyRequest().authenticated().and().formLogin().loginPage("/login")
        .successHandler(lastConnectionUpdateAuthenticationSuccessHandler).permitAll().and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/manager/logout")).permitAll();

    }

    String[] prepareAuthorizedUrls() {
      return new String[]{"/", "/actuator/**", "/websites/**", "/sites/**", "/pages/**",
        "/manager-websocket/**",
        "/robots", "/robot", "/robot.txt", "/robots.txt", "/webjars/**", "/js/**", "/img/**",
        "/css/**",
        "/**/favicon.ico", "/sitemap.xml", "/public/**", "/blog/**", "/widgets/**",
        "/forgotten_password",
        "/change_password"};
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
      return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(loginAuthenticationProvider);
    }

  }

  @Configuration
  public class InterceptorsConfiguration implements WebMvcConfigurer {

    public final PasswordTooOldInterceptor passwordTooOldInterceptor;

    public InterceptorsConfiguration(PasswordTooOldInterceptor passwordTooOldInterceptor) {
      this.passwordTooOldInterceptor = passwordTooOldInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

      registry.addInterceptor(passwordTooOldInterceptor).addPathPatterns("/manager/**");

    }
  }

}
