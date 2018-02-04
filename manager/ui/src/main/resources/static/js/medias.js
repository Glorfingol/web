$(document).ready(function () {
    $('#uploadMediaForm').submit(function (evt) {
        evt.preventDefault();


        var formData = new FormData(this);
        $.ajax({
            type: 'POST',
            url: "/manager/medias",
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            success: function () {
                window.location.href = "/manager/medias"
            },
            error: function () {
                window.location.href = "/manager/medias"
            }
        });
    });
})
