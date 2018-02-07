function cancelUpdateStyle() {
  window.location.href = "/manager/styles";
}

function validateAndUpdateStyle() {
  var style = computeUpdateStyle();
  return style;
}

function computeUpdateStyle() {
  var style = {};

  var inputId = $("#id");
  var inputCreationDate = $("#creationDate");
  var inputModificationDate = $("#modificationDate");
  var inputMediaName = $("#mediaName");
  var inputMediaId = $("#mediaId");

  style.id = inputId.val();
  style.menuTitle = inputCreationDate.val();
  style.withNews = inputModificationDate.val();
  style.content = computeStyleContent();
  style.mediaName = inputMediaName.val();
  style.mediaId = inputMediaId.val();
  return style;

}

function computeStyleContent() {
  if (codeMirrorStyleContent) {
    return codeMirrorStyleContent.getValue();
  }
  return $("#styleContent").val();
}

function postUpdateStyleForm() {
  var styleToUpdate = validateAndUpdateStyle();
  var url = "/manager/styles/_edit";
  var urlFallback = "/manager/styles";
  update($("#styleUpdateForm"), $(".loader"), $(".card-loader"), url,
      urlFallback, styleToUpdate);
}