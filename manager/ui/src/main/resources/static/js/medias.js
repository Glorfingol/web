$(document).ready(function () {
    $('#uploadMediaForm').submit(function (evt) {
        evt.preventDefault();

      var formData = new FormData(this);

      var url = "/manager/medias";
      var urlFallBack = "/manager/medias";
      upload($('#uploadMediaForm'), $(".loader"), $(".card-loader"),url,urlFallBack,formData);
    });
})
