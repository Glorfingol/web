package cmpl.web.style;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cmpl.web.core.model.BaseEntity;

@Entity(name = "style")
@Table(name = "style")
public class Style extends BaseEntity {

  @Column(name = "media_id")
  private String mediaId;

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

}
