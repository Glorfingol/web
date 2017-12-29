package com.cmpl.web.core.configuration;

import java.io.FileReader;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cmpl.web.core.model.BaseException;

/**
 * Configuration de la securite
 * 
 * @author Louis
 *
 */
@Configuration
@EnableWebSecurity
@PropertySource("classpath:/core/core.properties")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Value("${backUserFile}")
  String backUserJson;

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfiguration.class);

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    String[] authorizedUrls = prepareAuthorizedUrls();
    http.authorizeRequests().antMatchers(authorizedUrls).permitAll().anyRequest().authenticated().and().formLogin()
        .loginPage("/login").permitAll().and().logout().permitAll().and().csrf().ignoringAntMatchers("/h2console/**")
        .and().headers().frameOptions().sameOrigin();

  }

  /**
   * Configuration du user et mdp pour l'acces au back office
   * 
   * @param auth
   * @throws BaseException
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws BaseException {
    PasswordEncoder encoder = passwordEncoder();

    try {
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(new FileReader(backUserJson));
      JSONObject jsonObject = (JSONObject) obj;
      String user = (String) jsonObject.get("user");
      String password = (String) jsonObject.get("password");
      auth.inMemoryAuthentication().passwordEncoder(encoder).withUser(user).password(encoder.encode(password))
          .roles("USER");
    } catch (Exception e) {
      LOGGER.error("Erreur lors de la configuration de la sécurité", e);
      throw new BaseException(e.getMessage());
    }
  }

  String[] prepareAuthorizedUrls() {
    return new String[]{"/", "/pages/**", "/robots", "/robot", "/robot.txt", "/robots.txt", "/bootstrap/**",
        "/jquery/**", "/tether/**", "/fontawesome/**", "/ckeditor/**", "/js/**", "/img/**", "/css/**",
        "/**/favicon.ico", "/sitemap.xml", "/soon", "/public/**"};
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
}
