package cmpl.web.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import cmpl.web.core.model.BaseException;

/**
 * Configuration de la securite
 * 
 * @author Louis
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

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
    try {
      auth.inMemoryAuthentication().withUser("cmplUser").password("CmPlForTheWin!!").roles("USER");
    } catch (Exception e) {
      LOGGER.error("Erreur lors de la configuration de la sécurité", e);
      throw new BaseException(e.getMessage());
    }
  }

  String[] prepareAuthorizedUrls() {
    return new String[]{"/", "/pages/**", "/soins_medicaux", "/centre-medical", "/rendez-vous", "/contact",
        "/gynecologue", "/actualites", "/actualites/**", "/techniques", "/tarifs", "/horaires", "/robots", "/robot",
        "/robot.txt", "/robots.txt", "/bootstrap/**", "/jquery/**", "/tether/**", "/fontawesome/**", "/ckeditor/**",
        "/js/**", "/img/**", "/css/**", "/**/favicon.ico", "/sitemap.xml", "/soon"};
  }
}
