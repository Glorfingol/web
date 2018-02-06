var droppedFiles = false;
$(document).ready(function () {
  $('#uploadMediaForm').submit(function (evt) {
    evt.preventDefault();

    var formData = new FormData($('#uploadMediaForm')[0]);
    if (droppedFiles) {
      formData = new FormData();
      formData.append("media", droppedFiles[0]);
    }

    var url = "/manager/medias";
    var urlFallBack = "/manager/medias";
    upload($('#uploadMediaForm'), $(".loader"), $(".card-loader"), url,
        urlFallBack, formData);
  });
})
