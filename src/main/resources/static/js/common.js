$(document).ready(function(){
	$(".loader").hide();
	
	$("#loginForm").submit(function(){
		$(".loader").show();
		$("#loginForm").hide();
	});
	$("#connectToFacebook").submit(function(){
		$(".loader").show();
		$("#connectToFacebook").hide();
	});
});


$(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr) {
        xhr.setRequestHeader(header, token);
    });
});