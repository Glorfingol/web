package com.cmpl.web.core.user;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

@Entity(name = "user")
@Table(name = "user")
public class User extends BaseEntity {

  private String login;
  private String password;
  private LocalDateTime lastConnection;
  private String email;
  private String description;

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