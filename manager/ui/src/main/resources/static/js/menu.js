function cancelUpdateMenu(){
	window.location.href="/manager/menus";
}

function cancelCreateMenu(){
	window.location.href="/manager/menus";
}

function validateAndUpdateMenu(){
	var menu = computeMenuToUpdate();
	return menu;
}


function validateAndCreateMenu(){
	var menu = computeMenuToCreate();
	return menu;
}

function computeMenuToUpdate(){
	var menu = {};
	
	var inputTitle = $("#title");
	var inputPageId = $("#pageId");
	var inputParentId = $("#parentId");
	var inputLabel = $("#label");
	var inputHref = $("#href");
	var inputOrderInMenu = $("#orderInMenu");
	var inputId = $("#id");
	var inputCreationDate = $("#creationDate");
	var inputModificationDate = $("#modificationDate");
	
	menu.title = inputTitle.val();
	menu.pageId = inputPageId.val();
	menu.parentId = inputParentId.val();
	menu.label = inputLabel.val();
	menu.href = inputHref.val();
	menu.orderInMenu = inputOrderInMenu.val();
	menu.id = inputId.val();
	menu.creationDate = formatDate(inputCreationDate.val());
	menu.modificationDate = formatDate(inputModificationDate.val());
	
	return menu;
	
}

function computeMenuToCreate(){
	var menu = {};
	
	var inputTitle = $("#title");
	var inputPageId = $("#pageId");
	var inputParentId = $("#parentId");
	var inputLabel = $("#label");
	var inputHref = $("#href");
	var inputOrderInMenu = $("#orderInMenu");
	
	menu.title = inputTitle.val();
	menu.pageId = inputPageId.val();
	menu.parentId = inputParentId.val();
	menu.label = inputLabel.val();
	menu.href = inputHref.val();
	menu.orderInMenu = inputOrderInMenu.val();
	
	return menu;
	
}


function postUpdateMenuForm(){
	var menuToUpdate = validateAndUpdateMenu();
	var url = "/manager/menus/" + menuToUpdate.id;
	var urlFallback = "/manager/menus/" + menuToUpdate.id;
	update($("#menuEditForm"),$(".loader"),$(".card-loader"),url,urlFallback,menuToUpdate);
}

function postCreateMenuForm(){
	var menuToCreate = validateAndCreateMenu();
	var url = "/manager/menus/";
	var urlFallback = "/manager/menus/";
	create($("#menuCreateForm"),$(".loader"),$(".card-loader"),url,urlFallback,menuToCreate);
}