package com.cmpl.web.configuration.core.common;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WebSecurityConfigurationTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Spy
  WebSecurityConfiguration configuration;

  // @Test
  // public void testPrepareAuthorizedUrls() throws Exception {
  // String[] authorizedUrls = new String[]{"/", "/pages/**", "/robots", "/robot", "/robot.txt", "/robots.txt",
  // "/bootstrap/**", "/jquery/**", "/tether/**", "/fontawesome/**", "/ckeditor/**", "/codemirror/**", "/js/**",
  // "/img/**", "/css/**", "/**/favicon.ico", "/sitemap.xml", "/public/**", "/blog/**", "/widgets/**"};
  //
  // String[] result = configuration.prepareAuthorizedUrls();
  //
  // Assert.assertEquals(Arrays.asList(authorizedUrls), Arrays.asList(result));
  // }
  //
  // @Test
  // public void testConfigure() throws Exception {
  // ObjectPostProcessor<Object> objectProcessor = new ObjectPostProcessor<Object>() {
  //
  // @Override
  // public <O> O postProcess(O object) {
  // return null;
  // }
  // };
  // Map<Class<? extends Object>, Object> sharedObjects = new HashMap<>();
  // HttpSecurity http = new HttpSecurity(objectProcessor, new AuthenticationManagerBuilder(objectProcessor),
  // sharedObjects);
  // configuration.configure(http);
  //
  // }

  /*
   * @Test public void testConfigureGlobal() throws Exception { ObjectPostProcessor<Object> objectProcessor = new
   * ObjectPostProcessor<Object>() {
   * 
   * @Override public <O> O postProcess(O object) { return null; } }; AuthenticationManagerBuilder auth = new
   * AuthenticationManagerBuilder(objectProcessor); BackUser backUser =
   * BackUserBuilder.create().login("test").password("test").build();
   * BDDMockito.doReturn(backUser).when(configuration).computeBackUser(); configuration.configureGlobal(auth); }
   * 
   * @Test public void testConfigureGlobal_Exception() throws Exception { exception.expect(BaseException.class);
   * configuration.configureGlobal(null);
   * 
   * }
   */
}
