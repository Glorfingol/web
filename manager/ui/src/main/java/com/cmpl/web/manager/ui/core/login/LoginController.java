package com.cmpl.web.manager.ui.core.login;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.login.LoginDisplayFactory;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.user.ChangePasswordForm;
import com.cmpl.web.core.user.ChangePasswordResponse;
import com.cmpl.web.core.user.ChangePasswordResponseBuilder;
import com.cmpl.web.core.user.RequestPasswordLinkResponse;
import com.cmpl.web.core.user.RequestPasswordLinkResponseBuilder;
import com.cmpl.web.core.user.UserDispatcher;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller pour afficher la page de login
 *
 * @author Louis
 */
@Controller
public class LoginController {

  private final LoginDisplayFactory displayFactory;

  private final UserDispatcher userDispatcher;

  private final PluginRegistry<BackPage, String> backPagesRegistry;

  private final WebMessageSource messageSource;

  /**
   * Constructeur en autowired
   */
  public LoginController(LoginDisplayFactory displayFactory, UserDispatcher userDispatcher,
    PluginRegistry<BackPage, String> backPagesRegistry, WebMessageSource messageSource) {

    this.displayFactory = Objects.requireNonNull(displayFactory);
    this.userDispatcher = Objects.requireNonNull(userDispatcher);
    this.backPagesRegistry = backPagesRegistry;
    this.messageSource = messageSource;
  }

  /**
   * Mapping pour la page de login
   */
  @GetMapping(value = "/login")
  public ModelAndView printLogin() {
    return displayFactory.computeModelAndViewForBackPage(computeBackPage("LOGIN"), Locale.FRANCE);
  }

  @GetMapping(value = "/forgotten_password")
  public ModelAndView printForgottenPassword() {
    return displayFactory
      .computeModelAndViewForBackPage(computeBackPage("FORGOTTEN_PASSWORD"), Locale.FRANCE);
  }

  @PostMapping(value = "/forgotten_password")
  @ResponseBody
  public ResponseEntity<RequestPasswordLinkResponse> handleForgottenPassword(
    @RequestBody String email, Locale locale) {
    userDispatcher.sendChangePasswordLink(email, locale);
    return new ResponseEntity<>(RequestPasswordLinkResponseBuilder.create().build(), HttpStatus.OK);
  }

  @GetMapping(value = "/change_password")
  public ModelAndView printChangePassword(@RequestParam("token") String token, Locale locale) {

    ModelAndView changePasswordModel = displayFactory
      .computeModelAndViewForBackPage(computeBackPage("CHANGE_PASSWORD"),
        locale);
    changePasswordModel.addObject("token", token);
    return changePasswordModel;
  }

  @PostMapping(value = "/change_password")
  @ResponseBody
  public ResponseEntity<ChangePasswordResponse> handleChangePassword(
    @Valid @RequestBody ChangePasswordForm form,
    BindingResult bindingResult,
    Locale locale) {

    if (bindingResult.hasErrors()) {
      
      List<FieldError> translatedErrors =
        bindingResult.getAllErrors().stream()
          .map(error -> new FieldError(error.getObjectName(), error.getObjectName(),
            messageSource.getMessage(error.getDefaultMessage(), locale))).collect(
          Collectors.toList());

      return new ResponseEntity<>(
        ChangePasswordResponseBuilder.create().errors(translatedErrors).build(),
        HttpStatus.CONFLICT);
    }
    userDispatcher.changePassword(form, locale);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  protected BackPage computeBackPage(String pageName) {
    return backPagesRegistry.getPluginFor(pageName);
  }
}
