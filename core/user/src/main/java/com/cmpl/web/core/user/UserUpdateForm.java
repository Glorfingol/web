package com.cmpl.web.core.user;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.cmpl.web.core.common.form.BaseUpdateForm;

public class UserUpdateForm extends BaseUpdateForm<UserDTO> {

  private String login;
  private String password;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime lastConnection;
  private String email;
  private String description;

  public UserUpdateForm() {

  }

  public UserUpdateForm(UserDTO userDTO) {
    super(userDTO);
    this.login = userDTO.getLogin();
    this.password = userDTO.getPassword();
    this.lastConnection = userDTO.getLastConnection();
    this.email = userDTO.getEmail();
    this.description = userDTO.getDescription();
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LocalDateTime getLastConnection() {
    return lastConnection;
  }

  public void setLastConnection(LocalDateTime lastConnection) {
    this.lastConnection = lastConnection;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
