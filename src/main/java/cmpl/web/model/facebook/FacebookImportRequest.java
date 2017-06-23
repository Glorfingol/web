package cmpl.web.model.facebook;

import java.util.List;

public class FacebookImportRequest {

  private List<FacebookImportPost> postsToImport;

  public List<FacebookImportPost> getPostsToImport() {
    return postsToImport;
  }

  public void setPostsToImport(List<FacebookImportPost> postsToImport) {
    this.postsToImport = postsToImport;
  }

}
