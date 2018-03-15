package com.cmpl.web.configuration.core.common;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cmpl.web.manager.ui.core.security.LoginAuthenticationProvider;
import com.cmpl.web.manager.ui.core.user.LastConnectionUpdateAuthenticationSuccessHandlerImpl;

/**
 * Configuration de la securite
 * 
 * @author Louis
 *
 */
@Configuration
@EnableWebSecurity
@PropertySource("classpath:/core/core.properties")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfiguration {

  @Value("${backUserFile}")
  String backUserJson;

  @Bean
  public LoginAuthenticationProvider loginAuthenticationProvider(UserDetailsService dbUserDetailsService,
      PasswordEncoder passwordEncoder) {

    LoginAuthenticationProvider provider = new LoginAuthenticationProvider(dbUserDetailsService);
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
      throw new RuntimeException("Can't find the SHA1PRNG algorithm for generating random numbers", e);
    }
  }

  @Configuration
  public static class LoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    public final LoginAuthenticationProvider loginAuthenticationProvider;
    private final LastConnectionUpdateAuthenticationSuccessHandlerImpl lastConnectionUpdateAuthenticationSuccessHandler;

    public LoginWebSecurityConfigurerAdapter(LoginAuthenticationProvider loginAuthenticationProvider,
        LastConnectionUpdateAuthenticationSuccessHandlerImpl lastConnectionUpdateAuthenticationSuccessHandler) {
      this.loginAuthenticationProvider = loginAuthenticationProvider;
      this.lastConnectionUpdateAuthenticationSuccessHandler = lastConnectionUpdateAuthenticationSuccessHandler;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      String[] authorizedUrls = prepareAuthorizedUrls();
      http.headers().frameOptions().sameOrigin().and().authorizeRequests().antMatchers(authorizedUrls).permitAll()
          .anyRequest().authenticated().and().formLogin().loginPage("/login")
          .successHandler(lastConnectionUpdateAuthenticationSuccessHandler).permitAll().and().logout()
          .logoutRequestMatcher(new AntPathRequestMatcher("/manager/logout")).permitAll();

    }

    String[] prepareAuthorizedUrls() {
      return new String[]{"/", "/pages/**", "/robots", "/robot", "/robot.txt", "/robots.txt", "/bootstrap/**",
          "/jquery/**", "/tether/**", "/fontawesome/**", "/ckeditor/**", "/codemirror/**", "/js/**", "/img/**",
          "/css/**", "/**/favicon.ico", "/sitemap.xml", "/public/**", "/blog/**", "/widgets/**"};
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

}
