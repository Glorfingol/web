package cmpl.web.builder;

import org.springframework.social.facebook.api.Post.PostType;

import cmpl.web.model.facebook.FacebookImportPost;

public class FacebookImportPostBuilder {

  private String author;
  private String title;
  private PostType type;
  private String description;
  private String photoUrl;
  private String videoUrl;
  private String linkUrl;
  private String facebookId;
  private String creationDate;
  private String objectId;

  public FacebookImportPostBuilder author(String author) {
    this.author = author;
    return this;
  }

  public FacebookImportPostBuilder title(String title) {
    this.title = title;
    return this;
  }

  public FacebookImportPostBuilder type(PostType type) {
    this.type = type;
    return this;
  }

  public FacebookImportPostBuilder description(String description) {
    this.description = description;
    return this;
  }

  public FacebookImportPostBuilder photoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
    return this;
  }

  public FacebookImportPostBuilder videoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
    return this;
  }

  public FacebookImportPostBuilder linkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
    return this;
  }

  public FacebookImportPostBuilder facebookId(String facebookId) {
    this.facebookId = facebookId;
    return this;
  }

  public FacebookImportPostBuilder creationDate(String creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public FacebookImportPostBuilder objectId(String objectId) {
    this.objectId = objectId;
    return this;
  }

  public FacebookImportPost toFacebookImportPost() {

    FacebookImportPost importPost = new FacebookImportPost();
    importPost.setAuthor(author);
    importPost.setCreationDate(creationDate);
    importPost.setDescription(description);
    importPost.setFacebookId(facebookId);
    importPost.setLinkUrl(linkUrl);
    importPost.setObjectId(objectId);
    importPost.setPhotoUrl(photoUrl);
    importPost.setTitle(title);
    importPost.setType(type);
    importPost.setVideoUrl(videoUrl);

    return importPost;

  }

}
