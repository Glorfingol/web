package cmpl.web.core.configuration;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import cmpl.web.core.configuration.WebSecurityConfiguration;
import cmpl.web.core.model.BaseException;

@RunWith(MockitoJUnitRunner.class)
public class WebSecurityConfigurationTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Spy
  WebSecurityConfiguration configuration;

  @Test
  public void testPrepareAuthorizedUrls() throws Exception {
    String[] authorizedUrls = new String[]{"/", "/pages/**", "/soins_medicaux", "/centre-medical", "/rendez-vous",
        "/contact", "/gynecologue", "/actualites", "/actualites/**", "/techniques", "/tarifs", "/horaires", "/robots",
        "/robot", "/robot.txt", "/robots.txt", "/bootstrap/**", "/jquery/**", "/tether/**", "/fontawesome/**",
        "/ckeditor/**", "/js/**", "/img/**", "/css/**", "/**/favicon.ico", "/sitemap.xml", "/soon"};

    String[] result = configuration.prepareAuthorizedUrls();

    Assert.assertEquals(Lists.newArrayList(authorizedUrls), Lists.newArrayList(result));
  }

  @Test
  public void testConfigure() throws Exception {
    ObjectPostProcessor<Object> objectProcessor = new ObjectPostProcessor<Object>() {

      @Override
      public <O> O postProcess(O object) {
        return null;
      }
    };
    Map<Class<? extends Object>, Object> sharedObjects = new HashMap<>();
    HttpSecurity http = new HttpSecurity(objectProcessor, new AuthenticationManagerBuilder(objectProcessor),
        sharedObjects);
    configuration.configure(http);

  }

  @Test
  public void testConfigureGlobal() throws Exception {
    ObjectPostProcessor<Object> objectProcessor = new ObjectPostProcessor<Object>() {

      @Override
      public <O> O postProcess(O object) {
        return null;
      }
    };
    AuthenticationManagerBuilder auth = new AuthenticationManagerBuilder(objectProcessor);
    configuration.configureGlobal(auth);
  }

  @Test
  public void testConfigureGlobal_Exception() throws Exception {
    exception.expect(BaseException.class);
    configuration.configureGlobal(null);

  }
}
