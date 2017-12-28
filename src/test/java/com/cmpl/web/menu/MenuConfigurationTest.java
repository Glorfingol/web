package com.cmpl.web.menu;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.page.PageService;

@RunWith(MockitoJUnitRunner.class)
public class MenuConfigurationTest {

  @Mock
  private WebMessageSourceImpl messageSource;

  @Mock
  private MenuService menuService;
  @Mock
  private MenuValidator validator;
  @Mock
  private MenuTranslator translator;
  @Mock
  private PageService pageService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private MenuFactory menuFactory;

  @Spy
  @InjectMocks
  MenuConfiguration configuration;

  @Test
  public void testMenuFactory() throws Exception {
    Assert.assertEquals(MenuFactoryImpl.class, configuration.menuFactory(messageSource, menuService).getClass());

  }

  @Test
  public void testMenuValidator() throws Exception {
    Assert.assertEquals(MenuValidatorImpl.class, configuration.menuValidator(messageSource).getClass());
  }

  @Test
  public void testMenuTranslator() throws Exception {
    Assert.assertEquals(MenuTranslatorImpl.class, configuration.menuTranslator().getClass());
  }

  @Test
  public void testMenuDispatcher() throws Exception {
    Assert.assertEquals(MenuDispatcherImpl.class,
        configuration.menuDispatcher(validator, translator, menuService, pageService).getClass());
  }

  @Test
  public void testMenuManagerDisplayFactory() throws Exception {
    Assert.assertEquals(MenuManagerDisplayFactoryImpl.class,
        configuration.menuManagerDisplayFactory(menuFactory, messageSource, menuService, pageService, contextHolder)
            .getClass());
    ;
  }

}
