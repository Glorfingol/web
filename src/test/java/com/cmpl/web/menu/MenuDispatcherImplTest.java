package com.cmpl.web.menu;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.error.ErrorBuilder;
import com.cmpl.web.core.model.Error;
import com.cmpl.web.page.PageDTO;
import com.cmpl.web.page.PageDTOBuilder;
import com.cmpl.web.page.PageService;

@RunWith(MockitoJUnitRunner.class)
public class MenuDispatcherImplTest {

  @Mock
  private MenuValidator validator;
  @Mock
  private MenuTranslator translator;
  @Mock
  private MenuService menuService;
  @Mock
  private PageService pageService;

  @Spy
  @InjectMocks
  private MenuDispatcherImpl dispatcher;

  @Test
  public void testUpdateEntity_No_Error_ParentId() throws Exception {
    MenuUpdateForm form = new MenuUpdateFormBuilder().id(123456789l).href("someHref").label("someLabel")
        .title("someTitle").orderInMenu(1).pageId("123456789").parentId("123456789").build();
    MenuDTO menuToUpdate = new MenuDTOBuilder().build();

    BDDMockito.given(validator.validateUpdate(BDDMockito.any(MenuUpdateForm.class), BDDMockito.any(Locale.class)))
        .willReturn(null);

    BDDMockito.given(menuService.getEntity(BDDMockito.anyLong())).willReturn(menuToUpdate);
    BDDMockito.given(menuService.updateEntity(BDDMockito.any(MenuDTO.class))).willReturn(menuToUpdate);

    MenuResponse response = new MenuResponseBuilder().menu(menuToUpdate).build();
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(MenuDTO.class))).willReturn(response);

    Assert.assertEquals(response, dispatcher.updateEntity(form, Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).validateUpdate(BDDMockito.any(MenuUpdateForm.class),
        BDDMockito.any(Locale.class));
    BDDMockito.verify(menuService, BDDMockito.times(1)).getEntity(BDDMockito.anyLong());
    BDDMockito.verify(menuService, BDDMockito.times(1)).updateEntity(BDDMockito.any(MenuDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.any(MenuDTO.class));
    BDDMockito.verify(pageService, BDDMockito.times(0)).getEntity(BDDMockito.anyLong());

  }

  @Test
  public void testUpdateEntity_No_Error_No_ParentId() throws Exception {
    MenuUpdateForm form = new MenuUpdateFormBuilder().id(123456789l).href("someHref").label("someLabel")
        .title("someTitle").orderInMenu(1).pageId("123456789").build();
    MenuDTO menuToUpdate = new MenuDTOBuilder().pageId("123456789").build();

    BDDMockito.given(validator.validateUpdate(BDDMockito.any(MenuUpdateForm.class), BDDMockito.any(Locale.class)))
        .willReturn(null);

    BDDMockito.given(menuService.getEntity(BDDMockito.anyLong())).willReturn(menuToUpdate);
    BDDMockito.given(menuService.updateEntity(BDDMockito.any(MenuDTO.class))).willReturn(menuToUpdate);

    MenuResponse response = new MenuResponseBuilder().menu(menuToUpdate).build();
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(MenuDTO.class))).willReturn(response);

    PageDTO page = new PageDTOBuilder().name("someName").menuTitle("someMenuTitle").build();
    BDDMockito.given(pageService.getEntity(BDDMockito.anyLong())).willReturn(page);

    Assert.assertEquals(response, dispatcher.updateEntity(form, Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).validateUpdate(BDDMockito.any(MenuUpdateForm.class),
        BDDMockito.any(Locale.class));
    BDDMockito.verify(menuService, BDDMockito.times(1)).getEntity(BDDMockito.anyLong());
    BDDMockito.verify(menuService, BDDMockito.times(1)).updateEntity(BDDMockito.any(MenuDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.any(MenuDTO.class));
    BDDMockito.verify(pageService, BDDMockito.times(1)).getEntity(BDDMockito.anyLong());

  }

  @Test
  public void testUpdateEntity_Error() throws Exception {

    MenuUpdateForm form = new MenuUpdateFormBuilder().href("someHref").label("someLabel").title("someTitle")
        .orderInMenu(1).pageId("123456789").build();

    Error error = new ErrorBuilder().build();
    BDDMockito.given(validator.validateUpdate(BDDMockito.any(MenuUpdateForm.class), BDDMockito.any(Locale.class)))
        .willReturn(error);

    MenuResponse response = new MenuResponseBuilder().error(error).build();

    Assert.assertEquals(response.getError(), dispatcher.updateEntity(form, Locale.FRANCE).getError());
    BDDMockito.verify(validator, BDDMockito.times(1)).validateUpdate(BDDMockito.any(MenuUpdateForm.class),
        BDDMockito.any(Locale.class));
    BDDMockito.verify(menuService, BDDMockito.times(0)).getEntity(BDDMockito.anyLong());
    BDDMockito.verify(menuService, BDDMockito.times(0)).updateEntity(BDDMockito.any(MenuDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(0)).fromDTOToResponse(BDDMockito.any(MenuDTO.class));
    BDDMockito.verify(pageService, BDDMockito.times(0)).getEntity(BDDMockito.anyLong());
  }

  @Test
  public void testCreateEntity_No_Error_ParentId() throws Exception {
    MenuCreateForm form = new MenuCreateFormBuilder().href("someHref").label("someLabel").title("someTitle")
        .orderInMenu(1).pageId("123456789").parentId("123456789").build();
    MenuDTO menuToCreate = new MenuDTOBuilder().build();

    BDDMockito.given(validator.validateCreate(BDDMockito.any(MenuCreateForm.class), BDDMockito.any(Locale.class)))
        .willReturn(null);

    BDDMockito.given(menuService.createEntity(BDDMockito.any(MenuDTO.class))).willReturn(menuToCreate);

    MenuResponse response = new MenuResponseBuilder().menu(menuToCreate).build();
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(MenuDTO.class))).willReturn(response);
    BDDMockito.given(translator.fromCreateFormToDTO(BDDMockito.any(MenuCreateForm.class))).willReturn(menuToCreate);

    Assert.assertEquals(response, dispatcher.createEntity(form, Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).validateCreate(BDDMockito.any(MenuCreateForm.class),
        BDDMockito.any(Locale.class));
    BDDMockito.verify(menuService, BDDMockito.times(1)).createEntity(BDDMockito.any(MenuDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.any(MenuDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromCreateFormToDTO(BDDMockito.any(MenuCreateForm.class));
    BDDMockito.verify(pageService, BDDMockito.times(0)).getEntity(BDDMockito.anyLong());

  }

  @Test
  public void testCreateEntity_No_Error_No_ParentId() throws Exception {
    MenuCreateForm form = new MenuCreateFormBuilder().href("someHref").label("someLabel").title("someTitle")
        .orderInMenu(1).pageId("123456789").parentId("123456789").build();
    MenuDTO menuToCreate = new MenuDTOBuilder().pageId("123456789").build();

    BDDMockito.given(validator.validateCreate(BDDMockito.any(MenuCreateForm.class), BDDMockito.any(Locale.class)))
        .willReturn(null);

    BDDMockito.given(menuService.createEntity(BDDMockito.any(MenuDTO.class))).willReturn(menuToCreate);

    MenuResponse response = new MenuResponseBuilder().menu(menuToCreate).build();
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(MenuDTO.class))).willReturn(response);
    BDDMockito.given(translator.fromCreateFormToDTO(BDDMockito.any(MenuCreateForm.class))).willReturn(menuToCreate);

    PageDTO page = new PageDTOBuilder().name("someName").menuTitle("someMenuTitle").build();
    BDDMockito.given(pageService.getEntity(BDDMockito.anyLong())).willReturn(page);

    Assert.assertEquals(response, dispatcher.createEntity(form, Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).validateCreate(BDDMockito.any(MenuCreateForm.class),
        BDDMockito.any(Locale.class));
    BDDMockito.verify(menuService, BDDMockito.times(1)).createEntity(BDDMockito.any(MenuDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.any(MenuDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromCreateFormToDTO(BDDMockito.any(MenuCreateForm.class));
    BDDMockito.verify(pageService, BDDMockito.times(1)).getEntity(BDDMockito.anyLong());

  }

  @Test
  public void testCreateEntity_Error() throws Exception {

    MenuCreateForm form = new MenuCreateFormBuilder().href("someHref").label("someLabel").title("someTitle")
        .orderInMenu(1).pageId("123456789").build();

    Error error = new ErrorBuilder().build();
    BDDMockito.given(validator.validateCreate(BDDMockito.any(MenuCreateForm.class), BDDMockito.any(Locale.class)))
        .willReturn(error);

    MenuResponse response = new MenuResponseBuilder().error(error).build();

    Assert.assertEquals(response.getError(), dispatcher.createEntity(form, Locale.FRANCE).getError());
    BDDMockito.verify(validator, BDDMockito.times(1)).validateCreate(BDDMockito.any(MenuCreateForm.class),
        BDDMockito.any(Locale.class));
    BDDMockito.verify(menuService, BDDMockito.times(0)).createEntity(BDDMockito.any(MenuDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(0)).fromDTOToResponse(BDDMockito.any(MenuDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(0)).fromCreateFormToDTO(BDDMockito.any(MenuCreateForm.class));
    BDDMockito.verify(pageService, BDDMockito.times(0)).getEntity(BDDMockito.anyLong());
  }

}
