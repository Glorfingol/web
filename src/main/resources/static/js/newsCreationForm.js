var base64Image;
function validateAndCreateNewsEntry(){
	var newsEntry = computeNewsEntry();
	return newsEntry;
}

function computeNewsEntry(){
	var newsEntry = {};
	
	var inputTitle = $("#inputTitle");
	var inputAuthor = $("#inputAuthor");
	var inputTags = $("#inputTags");
	
	newsEntry.title = inputTitle.val();
	newsEntry.author = inputAuthor.val();
	newsEntry.tags = inputTags.val();
	var content = computeNewsContent();
	if(hasContent(content)){
		newsEntry.content = content;
	}
	var image = computeNewsImage();
	if(hasImage(image) || hasImageMetaData(image)){
		newsEntry.image  = image;
	}
	return newsEntry;
	
}

function hasContent(content){
	return content.content;
}

function hasImage(image){
	return image.src
}

function hasImageMetaData(image){
	return image.alt || image.legend || image.width || image.height;
}

function computeNewsContent(){
	var content = {};
	
	var inputContent = $("#inputContent");
	
	content.content = inputContent.val();
	
	return content;
}

function computeNewsImage(){
	var image = {};
	
	var inputLegend = $("#inputLegend");
	var inputAlt = $("#inputAlt");
	var inputWidth = $("#inputWidth");
	var inputHeight = $("#inputHeight");
	
	image.src= base64Image;
	image.alt = inputAlt.val();
	image.legend = inputLegend.val();
	image.width = inputWidth.val();
	image.height = inputHeight.val();
	
	return image;
}

function displayError(error){
	
	var causes = error.causes;
	if(causes && causes.length > 0){
		for(var i = 0; i< causes.length ; i++){
			var cause = causes[i];
			var code = cause.code;
			var message = cause.message;
			
			var errorTarget = getErrorTarget(code);
			errorTarget.addClass("has-error");
			
			var feedbackDiv = getFeedBackDivInput(code);
			feedbackDiv.hide();
			feedbackDiv.html(message);
			feedbackDiv.show();
			
			var errorInput = getErrorInput(code);
			errorInput.addClass("form-control-error");
			
		}
	}
}


function getFeedBackDivInput(code){
	if(code.indexOf("TITLE") !== -1){
		return  $("#feedbackTitle");
	}
	if(code.indexOf("AUTHOR") !== -1){
		return $("#feedbackAuthor");
	}	
	return null;
}

function getErrorInput(code){
	if(code.indexOf("TITLE") !== -1){
		return  $("#inputTitle");
	}
	if(code.indexOf("AUTHOR") !== -1){
		return $("#inputAuthor");
	}	
	return null;
}

function getErrorTarget(code){
	if(code.indexOf("TITLE") !== -1){
		return  $("#formInputTitle");
	}
	if(code.indexOf("AUTHOR") !== -1){
		return $("#formInputAuthor");
	}	
	return null;
}

function resetForm(){
	$("input").each(function(){
		$(this).val("");
		$(this).removeClass("form-control-error");
	});
	$(".form-group").each(function(){
		$(this).removeClass("has-error");
	});
	$(".control-label").each(function(){
		$(this).html("");
	});
	$("#imagePreview").attr("src","");
}

function previewFile() {
	  var preview = document.querySelector('img');
	  var file    = document.querySelector('input[type=file]').files[0];
	  var reader  = new FileReader();

	  reader.addEventListener("load", function () {
	    preview.src = reader.result;
	    base64Image = reader.result;
	  }, false);

	  if (file) {
	    reader.readAsDataURL(file);
	  }
}


