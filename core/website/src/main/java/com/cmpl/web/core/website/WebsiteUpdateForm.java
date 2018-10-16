package com.cmpl.web.core.website;

import com.cmpl.web.core.common.form.BaseUpdateForm;
import javax.validation.constraints.NotBlank;

public class WebsiteUpdateForm extends BaseUpdateForm<WebsiteDTO> {

  @NotBlank(message = "empty.website.name")
  private String name;

  @NotBlank(message = "empty.website.description")
  private String description;

  @NotBlank(message = "empty.website.extension")
  private String extension;

  private Boolean secure;

  private Boolean systemJquery;

  private Boolean systemBootstrap;

  private Boolean systemFontAwesome;

  public WebsiteUpdateForm() {

  }

  public WebsiteUpdateForm(WebsiteDTO websiteDTO) {
    super(websiteDTO);
    this.name = websiteDTO.getName();
    this.secure = websiteDTO.isSecure();
    this.description = websiteDTO.getDescription();
    this.extension = websiteDTO.getExtension();
    this.systemBootstrap = websiteDTO.isSystemBootstrap();
    this.systemJquery = websiteDTO.isSystemJquery();
    this.systemFontAwesome = websiteDTO.isSystemFontAwesome();
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getSecure() {
    return secure;
  }

  public void setSecure(Boolean secure) {
    this.secure = secure;
  }

  public Boolean getSystemJquery() {
    return systemJquery;
  }

  public void setSystemJquery(Boolean systemJquery) {
    this.systemJquery = systemJquery;
  }

  public Boolean getSystemBootstrap() {
    return systemBootstrap;
  }

  public void setSystemBootstrap(Boolean systemBootstrap) {
    this.systemBootstrap = systemBootstrap;
  }

  public Boolean getSystemFontAwesome() {
    return systemFontAwesome;
  }

  public void setSystemFontAwesome(Boolean systemFontAwesome) {
    this.systemFontAwesome = systemFontAwesome;
  }
}
