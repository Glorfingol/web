var base64Image;
$(document).ready(function () {
  $(".card-loader").hide();
  $(".loader").hide();

  $("#loginForm").submit(function () {
    $(".card-loader").show();
    $(".loader").show();
    $("#loginForm").hide();
  });
  $("#connectToFacebook").submit(function () {
    $(".card-loader").show();
    $(".loader").show();
    $("#connectToFacebook").hide();
  });
  fix_height();
});

$(function () {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");
  $(document).ajaxSend(function (e, xhr) {
    xhr.setRequestHeader(header, token);
  });
});

function formatDate(date) {
  return date;
}

function getFeedBackDivInput(code) {
  if (code.indexOf("TITLE") !== -1) {
    return $("#feedbackTitle");
  }
  if (code.indexOf("AUTHOR") !== -1) {
    return $("#feedbackAuthor");
  }
  if (code.indexOf("CONTENT") !== -1) {
    return $("#feedbackContent");
  }
  if (code.indexOf("LEGEND") !== -1) {
    return $("#feedbackLegend");
  }
  if (code.indexOf("SRC") !== -1) {
    return $("#feedbackImage");
  }
  if (code.indexOf("FORMAT") !== -1) {
    return $("#feedbackImage");
  }
  if (code.indexOf("ALT") !== -1) {
    return $("#feedbackAlt");
  }
  if (code.indexOf("NAME") !== -1) {
    return $("#feedbackName");
  }
  if (code.indexOf("MENU_TITLE") !== -1) {
    return $("#feedbackMenuTitle");
  }
  return null;
}

function getErrorInput(code) {
  if (code.indexOf("TITLE") !== -1) {
    return $("#title");
  }
  if (code.indexOf("AUTHOR") !== -1) {
    return $("#zuthor");
  }
  if (code.indexOf("CONTENT") !== -1) {
    return $("#content");
  }
  if (code.indexOf("LEGEND") !== -1) {
    return $("#legend");
  }
  if (code.indexOf("SRC") !== -1) {
    return $("#image");
  }
  if (code.indexOf("FORMAT") !== -1) {
    return $("#image");
  }
  if (code.indexOf("ALT") !== -1) {
    return $("#alt");
  }
  if (code.indexOf("NAME") !== -1) {
    return $("#name");
  }
  if (code.indexOf("MENU_TITLE") !== -1) {
    return $("#menuTitle");
  }
  return null;
}

function getErrorTarget(code) {
  if (code.indexOf("TITLE") !== -1) {
    return $("#formInputTitle");
  }
  if (code.indexOf("AUTHOR") !== -1) {
    return $("#formInputAuthor");
  }
  if (code.indexOf("CONTENT") !== -1) {
    return $("#formInputContent");
  }
  if (code.indexOf("LEGEND") !== -1) {
    return $("#formInputLegend");
  }
  if (code.indexOf("SRC") !== -1) {
    return $("#formInputImage");
  }
  if (code.indexOf("FORMAT") !== -1) {
    return $("#formInputImage");
  }
  if (code.indexOf("ALT") !== -1) {
    return $("#formInputAlt");
  }
  if (code.indexOf("NAME") !== -1) {
    return $("#formInputName");
  }
  if (code.indexOf("MENU_TITLE") !== -1) {
    return $("#formInputMenuTitle");
  }
  return null;
}

function displayError(error) {

  var causes = error.causes;
  if (causes && causes.length > 0) {
    for (var i = 0; i < causes.length; i++) {
      var cause = causes[i];
      var code = cause.code;
      var message = cause.message;

      var errorTarget = getErrorTarget(code);
      errorTarget.addClass("has-danger");

      var feedbackDiv = getFeedBackDivInput(code);
      feedbackDiv.html(message);

      var errorInput = getErrorInput(code);
      errorInput.addClass("form-control-danger");

    }
  }
}

function update(formToToggle, loader, cardLoader, url, urlFallBack, dataToSend,
    stay) {

  formToToggle.hide();
  loader.show();
  cardLoader.show();
  var data = JSON.stringify(dataToSend);
  $.ajax({
    type: "PUT",
    url: url,
    data: data,
    contentType: "application/json; charset=utf-8",
    crossDomain: true,
    dataType: "json",
    success: function (data) {
      cardLoader.hide();
      if (data.error) {
        loader.hide();
        formToToggle.show();
        displayError(data.error);
      } else {
        if (!stay) {
          window.location.href = urlFallBack;
        }
        loader.hide();
        formToToggle.show();
      }
    },
    error: function () {
      window.location.href = urlFallBack;
    }
  });
}

function create(formToToggle, loader, cardLoader, url, urlFallBack,
    dataToSend) {
  formToToggle.hide();
  loader.show();
  cardLoader.show();
  var data = JSON.stringify(dataToSend);
  $.ajax({
    type: "POST",
    url: url,
    data: data,
    contentType: "application/json; charset=utf-8",
    crossDomain: true,
    dataType: "json",
    success: function (data) {
      cardLoader.hide();
      if (data.error) {
        loader.hide();
        formToToggle.show();
        displayError(data.error);
      } else {
        window.location.href = url;
      }
    },
    error: function () {
      window.location.href = url;
    }
  });
}

function upload(formToToggle, loader, cardLoader, url, urlFallBack,
    dataToSend) {
  formToToggle.hide();
  loader.show();
  cardLoader.show();
  var data = dataToSend;
  $.ajax({
    type: "POST",
    url: url,
    enctype: 'multipart/form-data',
    processData: false,
    data: data,
    contentType: false,
    crossDomain: true,
    cache: false,
    success: function (data) {
      cardLoader.hide();
      if (data.error) {
        loader.hide();
        formToToggle.show();
        displayError(data.error);
      } else {
        window.location.href = url;
      }
    },
    error: function () {
      window.location.href = url;
    }
  });
}

function fix_height() {
  $('#page-wrapper').css("min-height", $(window).height() - 60 + "px");
}