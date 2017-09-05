var base64Image;
function validateAndCreateNewsEntry(){
	var newsEntry = computeNewsEntry();
	return newsEntry;
}

function computeNewsEntry(){
	var newsEntry = {};
	
	var inputTitle = $("#title");
	var inputAuthor = $("#author");
	var inputTags = $("#tags");
	
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
	content.content = CKEDITOR.instances.newsContent.getData();
	return content;
}

function computeNewsImage(){
	var image = {};
	
	var inputLegend = $("#image\\.legend");
	var inputAlt = $("#image\\.alt");
	
	image.src= base64Image;
	image.alt = inputAlt.val();
	image.legend = inputLegend.val();
	
	return image;
}

function displayNewsError(error){
	
	var causes = error.causes;
	if(causes && causes.length > 0){
		for(var i = 0; i< causes.length ; i++){
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


function getFeedBackDivInput(code){
	if(code.indexOf("TITLE") !== -1){
		return  $("#feedbackTitle");
	}
	if(code.indexOf("AUTHOR") !== -1){
		return $("#feedbackAuthor");
	}	
	if(code.indexOf("CONTENT") !== -1){
		return $("#feedbackContent");
	}	
	if(code.indexOf("LEGEND") !== -1){
		return $("#feedbackLegend");
	}	
	if(code.indexOf("SRC") !== -1){
		return $("#feedbackImage");
	}	
	if(code.indexOf("FORMAT") !== -1){
		return $("#feedbackImage");
	}	
	if(code.indexOf("ALT") !== -1){
		return $("#feedbackAlt");
	}	
	return null;
}

function getErrorInput(code){
	if(code.indexOf("TITLE") !== -1){
		return  $("#title");
	}
	if(code.indexOf("AUTHOR") !== -1){
		return $("#zuthor");
	}	
	if(code.indexOf("CONTENT") !== -1){
		return $("#content");
	}	
	if(code.indexOf("LEGEND") !== -1){
		return $("#legend");
	}	
	if(code.indexOf("SRC") !== -1){
		return $("#image");
	}	
	if(code.indexOf("FORMAT") !== -1){
		return $("#image");
	}	
	if(code.indexOf("ALT") !== -1){
		return $("#alt");
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
	if(code.indexOf("CONTENT") !== -1){
		return $("#formInputContent");
	}	
	if(code.indexOf("LEGEND") !== -1){
		return $("#formInputLegend");
	}	
	if(code.indexOf("SRC") !== -1){
		return $("#formInputImage");
	}	
	if(code.indexOf("FORMAT") !== -1){
		return $("#formInputImage");
	}	
	if(code.indexOf("ALT") !== -1){
		return $("#formInputAlt");
	}	
	return null;
}

function postCreateNewsForm(){
	
	$("#newsEntryCreateForm").hide();
	$(".loader").show();
	var url = "/manager/news";
	var data = JSON.stringify(validateAndCreateNewsEntry());
	$.ajax({
        type: "POST",
        url: url,
        data: data,
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        dataType: "json",
        success: function (data) {
        	$("#newsEntryCreateForm").show();
        	$(".loader").hide();
        	if(data.error){
        		displayNewsError(data.error);
        	}else{
        		window.location.href= url;
        	}
        },
        error: function(){
        	window.location.href= url;
        }
     });
}

