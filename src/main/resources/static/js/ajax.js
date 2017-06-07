$(document).ready(function(){
	$(".loader").hide();
});

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
        		displayError(data.error);
        	}else{
        		window.location.href= url;
        	}
        }
     });
}

function postUpdateNewsForm(){
	
	$("#newsEntryEditForm").hide();
	$(".loader").show();
	var newsEntryToUpdate = validateAndUpdateNewsEntry();
	var url = "/manager/news/" + newsEntryToUpdate.id;
	var urlFallback = "/manager/news";
	var data = JSON.stringify(newsEntryToUpdate);
	$.ajax({
        type: "PUT",
        url: url,
        data: data,
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        dataType: "json",
        success: function (data) {
        	$("#newsEntryEditForm").show();
    		$(".loader").hide();
        	if(data.error){
        		displayError(data.error);
        	}else{
        		window.location.href= urlFallback;
        	}
        }
     });
}

$(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});