package cmpl.web.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import cmpl.web.model.BaseException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfiguration.class);

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    String[] authorizedUrls = prepareAuthorizedUrls();
    http.authorizeRequests().antMatchers(authorizedUrls).permitAll().anyRequest().authenticated().and().formLogin()
        .loginPage("/login").permitAll().and().logout().permitAll();
  }

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
    return new String[]{"/", "/soins_medicaux", "/centre-medical", "/rendez-vous", "/contact", "/gynecologue",
        "/actualites", "/actualites/**", "/techniques", "/tarifs", "/horaires", "/robots", "/robot", "/robot.txt",
        "/robots.txt", "/bootstrap/**", "/jquery/**", "/tether/**", "/js/**", "/img/**", "/css/**", "/**/favicon.ico",
        "/sitemap.xml", "/soon"};
  }
}
