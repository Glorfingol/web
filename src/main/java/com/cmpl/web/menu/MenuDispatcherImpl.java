package com.cmpl.web.menu;

import java.util.Locale;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.model.Error;
import com.cmpl.web.page.PageDTO;
import com.cmpl.web.page.PageService;

public class MenuDispatcherImpl implements MenuDispatcher {

  private final MenuValidator validator;
  private final MenuTranslator translator;
  private final MenuService menuService;
  private final PageService pageService;

  public MenuDispatcherImpl(MenuValidator validator, MenuTranslator translator, MenuService menuService,
      PageService pageService) {
    this.validator = validator;
    this.translator = translator;
    this.menuService = menuService;
    this.pageService = pageService;
  }

  @Override
  public MenuResponse createEntity(MenuCreateForm form, Locale locale) {

    Error error = validator.validateCreate(form, locale);

    if (error != null) {
      MenuResponse response = new MenuResponse();
      response.setError(error);
      return response;
    }

    MenuDTO menuToCreate = translator.fromCreateFormToDTO(form);

    if (!StringUtils.hasText(menuToCreate.getParentId()) && StringUtils.hasText(menuToCreate.getPageId())) {
      PageDTO page = pageService.getEntity(Long.valueOf(menuToCreate.getPageId()));
      menuToCreate.setLabel(page.getMenuTitle());
      menuToCreate.setHref("/pages/" + page.getName());
    }

    MenuDTO createdMenu = menuService.createEntity(menuToCreate);
    return translator.fromDTOToResponse(createdMenu);
  }

  @Override
  public MenuResponse updateEntity(MenuUpdateForm form, Locale locale) {

    Error error = validator.validateUpdate(form, locale);

    if (error != null) {
      MenuResponse response = new MenuResponse();
      response.setError(error);
      return response;
    }

    MenuDTO menuToUpdate = menuService.getEntity(form.getId());
    menuToUpdate.setHref(form.getHref());
    menuToUpdate.setLabel(form.getLabel());
    menuToUpdate.setOrderInMenu(form.getOrderInMenu());
    menuToUpdate.setPageId(form.getPageId());
    menuToUpdate.setParentId(form.getParentId());
    menuToUpdate.setTitle(form.getTitle());

    if (!StringUtils.hasText(menuToUpdate.getParentId()) && StringUtils.hasText(menuToUpdate.getPageId())) {
      PageDTO page = pageService.getEntity(Long.valueOf(menuToUpdate.getPageId()));
      menuToUpdate.setLabel(page.getMenuTitle());
      menuToUpdate.setHref("/pages/" + page.getName());
    }

    MenuDTO updatedMenu = menuService.updateEntity(menuToUpdate);

    return translator.fromDTOToResponse(updatedMenu);
  }

}
