package cmpl.web.model.login;

public class LoginFormDisplayBean {

  private String userLabel;
  private String passwordLabel;
  private String errorLabel;
  private String timeoutLabel;

  public String getUserLabel() {
    return userLabel;
  }

  public void setUserLabel(String userLabel) {
    this.userLabel = userLabel;
  }

  public String getPasswordLabel() {
    return passwordLabel;
  }

  public void setPasswordLabel(String passwordLabel) {
    this.passwordLabel = passwordLabel;
  }

  public String getErrorLabel() {
    return errorLabel;
  }

  public void setErrorLabel(String errorLabel) {
    this.errorLabel = errorLabel;
  }

  public String getTimeoutLabel() {
    return timeoutLabel;
  }

  public void setTimeoutLabel(String timeoutLabel) {
    this.timeoutLabel = timeoutLabel;
  }

}
