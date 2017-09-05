function validateAndCreatePage(){
    var page = computePage();
    return page;
}


function computePage(){
    var page = {};
    
    var inputName = $("#name");
    var inputMenuTitle = $("#menuTitle");
    var inputWithNews = $("#withNews");
    
    page.name = inputName.val();
    page.menuTitle = inputMenuTitle.val();
    page.withNews = inputWithNews.val() === "true" ? true : false;
    page.body = computePageBody();
    return page;
    
}

function computePageBody(){
	return CKEDITOR.instances.pageBody.getData();
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
	if(code.indexOf("NAME") !== -1){
		return  $("#feedbackName");
	}
	if(code.indexOf("MENU_TITLE") !== -1){
		return $("#feedbackMenuTitle");
	}	
	return null;
}

function getErrorInput(code){
	if(code.indexOf("NAME") !== -1){
		return  $("#name");
	}
	if(code.indexOf("MENU_TITLE") !== -1){
		return $("#menuTitle");
	}	
	return null;
}

function getErrorTarget(code){
	if(code.indexOf("NAME") !== -1){
		return  $("#formInputName");
	}
	if(code.indexOf("MENU_TITLE") !== -1){
		return $("#formInputMenuTitle");
	}
	return null;
}


function postCreatePageForm(){
	
	$("#pageCreateForm").hide();
	$(".loader").show();
	var url = "/manager/pages";
	var data = JSON.stringify(validateAndCreatePage());
	$.ajax({
        type: "POST",
        url: url,
        data: data,
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        dataType: "json",
        success: function (data) {
        	$("#pageCreateForm").show();
        	$(".loader").hide();
        	if(data.error){
        		displayPageError(data.error);
        	}else{
        		window.location.href= url;
        	}
        },
        error: function(){
        	window.location.href= url;
        }
     });
}