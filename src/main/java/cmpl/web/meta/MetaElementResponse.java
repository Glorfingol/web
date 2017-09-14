package cmpl.web.meta;

import cmpl.web.core.model.BaseResponse;

public class MetaElementResponse extends BaseResponse {

  private MetaElementDTO metaElement;
  private OpenGraphMetaElementDTO openGraphMetaElement;

  public MetaElementDTO getMetaElement() {
    return metaElement;
  }

  public void setMetaElement(MetaElementDTO metaElement) {
    this.metaElement = metaElement;
  }

  public OpenGraphMetaElementDTO getOpenGraphMetaElement() {
    return openGraphMetaElement;
  }

  public void setOpenGraphMetaElement(OpenGraphMetaElementDTO openGraphMetaElement) {
    this.openGraphMetaElement = openGraphMetaElement;
  }

}
