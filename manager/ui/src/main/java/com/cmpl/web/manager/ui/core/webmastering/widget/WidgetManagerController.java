package com.cmpl.web.manager.ui.core.webmastering.widget;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;
import com.cmpl.web.core.factory.widget.WidgetManagerDisplayFactory;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetCreateForm;
import com.cmpl.web.core.widget.WidgetDispatcher;
import com.cmpl.web.core.widget.WidgetResponse;
import com.cmpl.web.core.widget.WidgetUpdateForm;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ManagerController
@RequestMapping(value = "/manager/widgets")
public class WidgetManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(WidgetManagerController.class);

  private final WidgetManagerDisplayFactory widgetManagerDisplayFactory;

  private final WidgetDispatcher widgetDispatcher;

  private final NotificationCenter notificationCenter;

  private final WebMessageSource messageSource;

  private final PluginRegistry<WidgetProviderPlugin, String> widgetProviders;


  public WidgetManagerController(WidgetManagerDisplayFactory widgetManagerDisplayFactory,
    WidgetDispatcher widgetDispatcher, NotificationCenter notificationCenter,
    WebMessageSource messageSource, PluginRegistry<WidgetProviderPlugin, String> widgetProviders) {
    this.widgetManagerDisplayFactory = Objects.requireNonNull(widgetManagerDisplayFactory);
    this.widgetDispatcher = Objects.requireNonNull(widgetDispatcher);
    this.notificationCenter = Objects.requireNonNull(notificationCenter);
    this.messageSource = Objects.requireNonNull(messageSource);
    this.widgetProviders = Objects.requireNonNull(widgetProviders);

  }

  @GetMapping
  @PreAuthorize("hasAuthority('webmastering:widgets:read')")
  public ModelAndView printViewWidgets(
    @RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    return widgetManagerDisplayFactory
      .computeModelAndViewForViewAllWidgets(locale, pageNumberToUse);
  }

  @GetMapping(value = "/search")
  @PreAuthorize("hasAuthority('webmastering:widgets:read')")
  public ModelAndView printSearchWidgets(
    @RequestParam(name = "p", required = false) Integer pageNumber,
    @RequestParam(name = "q") String query, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    return widgetManagerDisplayFactory
      .computeModelAndViewForAllEntitiesTab(locale, pageNumberToUse, query);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  @PreAuthorize("hasAuthority('webmastering:widgets:create')")
  public ModelAndView printCreateWidget(Locale locale) {
    return widgetManagerDisplayFactory.computeModelAndViewForCreateWidget(locale);
  }

  @PostMapping(produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:widgets:create')")
  public ResponseEntity<WidgetResponse> createWidget(
    @Valid @RequestBody WidgetCreateForm createForm,
    BindingResult bindingResult, Locale locale) {
    LOGGER.info("Tentative de création d'une page");

    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("create.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      WidgetResponse response = widgetDispatcher
        .createEntity(createForm, computeDefaultPersonalizationContentForWidget(createForm));

      LOGGER.info("Entrée crée, id " + response.getWidget().getId());

      notificationCenter
        .sendNotification("success", messageSource.getMessage("create.success", locale));

      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter
        .sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  private String computeDefaultPersonalizationContentForWidget(WidgetCreateForm form) {
    WidgetProviderPlugin provider = widgetProviders.getPluginFor(form.getType());
    String template = provider.computeDefaultWidgetTemplate();

    try {
      Path path = Paths.get(getClass().getClassLoader()
        .getResource(template + ".html").toURI());
      StringBuilder data = new StringBuilder();
      Stream<String> lines = Files.lines(path);
      lines.forEach(line -> data.append(line).append("\n"));
      lines.close();
    } catch (Exception e) {
      LOGGER.error("Impossible de lre le template par défaut {}", template, e);
    }
    return "";
  }

  @GetMapping(value = "/{widgetId}")
  @PreAuthorize("hasAuthority('webmastering:widgets:read')")
  public ModelAndView printViewUpdateWidget(@PathVariable(value = "widgetId") String widgetId,
    Locale locale,
    @RequestParam(name = "languageCode", required = false) String languageCode) {
    return widgetManagerDisplayFactory
      .computeModelAndViewForUpdateWidget(locale, widgetId, languageCode);
  }

  @GetMapping(value = "/{widgetId}/_main")
  @PreAuthorize("hasAuthority('webmastering:widgets:read')")
  public ModelAndView printViewUpdateWidgetMain(@PathVariable(value = "widgetId") String
    widgetId,
    Locale locale,
    @RequestParam(name = "languageCode", required = false) String languageCode) {
    return widgetManagerDisplayFactory
      .computeModelAndViewForUpdateWidgetMain(locale, widgetId, languageCode);
  }

  @GetMapping(value = "/{widgetId}/_personalization")
  @PreAuthorize("hasAuthority('webmastering:widgets:read')")
  public ModelAndView printViewUpdateWidgetPersonalization(
    @PathVariable(value = "widgetId") String widgetId,
    Locale locale, @RequestParam(name = "languageCode", required = false) String languageCode) {
    return widgetManagerDisplayFactory
      .computeModelAndViewForUpdateWidgetPersonalization(locale, widgetId,
        languageCode);
  }

  @GetMapping(value = "/{widgetId}/_memberships")
  @PreAuthorize("hasAuthority('webmastering:widgets:read')")
  public ModelAndView printViewUpdateWidgetMemberships(
    @PathVariable(value = "widgetId") String widgetId) {
    return widgetManagerDisplayFactory.computeModelAndViewForMembership(widgetId);
  }

  @PutMapping(value = "/{widgetId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:widgets:write')")
  public ResponseEntity<WidgetResponse> updateWidget(
    @Valid @RequestBody WidgetUpdateForm updateForm,
    BindingResult bindingResult, Locale locale) {
    LOGGER.info("Tentative de création d'une page");

    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("update.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    try {
      WidgetResponse response = widgetDispatcher.updateEntity(updateForm, locale);

      LOGGER.info("Entrée modifiée, id " + response.getWidget().getId());

      notificationCenter
        .sendNotification("success", messageSource.getMessage("update.success", locale));

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification de l'entrée", e);
      notificationCenter
        .sendNotification("danger", messageSource.getMessage("update.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @DeleteMapping(value = "/{widgetId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:widgets:delete')")
  public ResponseEntity<WidgetResponse> deleteWidget(
    @PathVariable(value = "widgetId") String widgetId, Locale locale) {
    LOGGER.info("Tentative de création d'une page");

    try {
      WidgetResponse response = widgetDispatcher.deleteEntity(widgetId, locale);

      notificationCenter
        .sendNotification("success", messageSource.getMessage("delete.success", locale));
      return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error("Echec de la suppression de l'entrée", e);
      notificationCenter
        .sendNotification("danger", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }
}
