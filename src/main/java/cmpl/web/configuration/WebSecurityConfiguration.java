package cmpl.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    String[] authorizedUrls = new String[]{"/", "/soins_medicaux", "/centre-medical", "/rendez-vous", "/contact",
        "/gynecologue", "/actualites", "/techniques", "/tarifs", "/horaires", "/robots", "/robot", "/robot.txt",
        "/robots.txt", "/bootstrap/**", "/jquery/**", "/js/**", "/img/**", "/css/**", "/**/favicon.ico"};
    http.authorizeRequests().antMatchers(authorizedUrls).permitAll().anyRequest().authenticated().and().formLogin()
        .loginPage("/login").permitAll().and().logout().permitAll();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("cmplUser").password("CmPlForTheWin!!").roles("USER");
  }

}
