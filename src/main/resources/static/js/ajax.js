function postCreateNewsForm(){
	
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
        	if(data.error){
        		displayError(data.error);
        	}else{
        		window.location.href= url;
        	}
        }
     });
}