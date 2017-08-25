package cmpl.web.page;

import java.util.List;

import cmpl.web.core.model.BaseDTO;
import cmpl.web.meta.MetaElementDTO;
import cmpl.web.meta.OpenGraphMetaElementDTO;

/**
 * DTO Page
 * 
 * @author Louis
 *
 */
public class PageDTO extends BaseDTO {

  private String name;
  private String menuTitle;
  private String body;

  private List<MetaElementDTO> metaElements;
  private List<OpenGraphMetaElementDTO> openGraphMetaElements;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMenuTitle() {
    return menuTitle;
  }

  public void setMenuTitle(String menuTitle) {
    this.menuTitle = menuTitle;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public List<MetaElementDTO> getMetaElements() {
    return metaElements;
  }

  public void setMetaElements(List<MetaElementDTO> metaElements) {
    this.metaElements = metaElements;
  }

  public List<OpenGraphMetaElementDTO> getOpenGraphMetaElements() {
    return openGraphMetaElements;
  }

  public void setOpenGraphMetaElements(List<OpenGraphMetaElementDTO> openGraphMetaElements) {
    this.openGraphMetaElements = openGraphMetaElements;
  }

}
