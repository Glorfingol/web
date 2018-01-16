function validateAndCreatePage(){
    var page = computePage();
    return page;
}

function cancelUpdatePage(){
	window.location.href="/manager/pages";
}

function validateAndUpdatePage(){
	var page = computeUpdatePage();
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
    page.header = computePageHeader();
    page.footer = computePageHeader();
    return page;
    
}


function computeUpdatePage(){
    var page = {};
    
    var inputName = $("#name");
    var inputMenuTitle = $("#menuTitle");
    var inputWithNews = $("#withNews");
    var inputId = $("#id");
    var inputCreationDate = $("#creationDate");
    var inputModificationDate = $("#modificationDate");
    
    page.name = inputName.val();
    page.menuTitle = inputMenuTitle.val();
    page.withNews = inputWithNews.val() === "true" ? true : false;
    page.body = computePageBody();
    page.header = computePageHeader();
    page.footer = computePageHeader();
    page.id = inputId.val();
    page.creationDate = inputCreationDate.val();
    page.modificationDate = inputModificationDate.val();
    return page;
}

function computePageBody(){
	if(CKEDITOR.instances.pageBody){
		return CKEDITOR.instances.pageBody.getData();
	}
	return $("#body").val();
}

function computePageHeader(){
	if(CKEDITOR.instances.pageHeader){
		return CKEDITOR.instances.pageHeader.getData();
	}
	return $("#header").val();
}

function computePageFooter(){
	if(CKEDITOR.instances.pageBody){
		return CKEDITOR.instances.pageFooter.getData();
	}
	return $("#footer").val();
}

function postCreatePageForm(){
	var url = "/manager/pages";
	create($("#pageCreateForm"),$(".loader"),$(".card-loader"),url,url,validateAndCreatePage());
}


function postUpdatePageForm(){
	var pageToUpdate = validateAndUpdatePage();
	var url = "/manager/pages/" + pageToUpdate.id;
	var urlFallback = "/manager/pages/" + pageToUpdate.id;
	update($("#pageUpdateForm"),$(".loader"),$(".card-loader"),url,urlFallback,pageToUpdate);
}