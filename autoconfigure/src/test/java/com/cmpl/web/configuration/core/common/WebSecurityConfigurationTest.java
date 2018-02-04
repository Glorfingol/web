package com.cmpl.web.configuration.core.common;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.common.user.BackUser;
import com.cmpl.web.core.common.user.BackUserBuilder;

@RunWith(MockitoJUnitRunner.class)
public class WebSecurityConfigurationTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Spy
  WebSecurityConfiguration configuration;

  @Test
  public void testPrepareAuthorizedUrls() throws Exception {
    String[] authorizedUrls = new String[]{"/", "/pages/**", "/robots", "/robot", "/robot.txt", "/robots.txt",
        "/bootstrap/**", "/jquery/**", "/tether/**", "/fontawesome/**", "/ckeditor/**", "/js/**", "/img/**", "/css/**",
        "/**/favicon.ico", "/sitemap.xml", "/public/**", "/blog/**", "/widgets/**"};

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
    BackUser backUser = BackUserBuilder.create().login("test").password("test").build();
    BDDMockito.doReturn(backUser).when(configuration).computeBackUser();
    configuration.configureGlobal(auth);
  }

  @Test
  public void testConfigureGlobal_Exception() throws Exception {
    exception.expect(BaseException.class);
    configuration.configureGlobal(null);

  }
}
