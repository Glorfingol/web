var base64Image;
function validateAndUpdateNewsEntry(){
	var newsEntry = computeNewsEntryUpdate();
	return newsEntry;
}

function cancelUpdate(){
	window.location.href="/manager/news";
}

function computeNewsEntryUpdate(){
	var newsEntry = {};
	
	var inputTitle = $("#inputTitle");
	var inputAuthor = $("#inputAuthor");
	var inputTags = $("#inputTags");
	var inputId = $("#entryId");
	var inputCreationDate = $("#entryCreationDate");
	var inputModificationDate = $("#entryModificationDate");
	
	newsEntry.title = inputTitle.val();
	newsEntry.author = inputAuthor.val();
	newsEntry.tags = inputTags.val();
	newsEntry.id = inputId.val();
	newsEntry.creationDate = formatDate(inputCreationDate.val());
	newsEntry.modificationDate = formatDate(inputModificationDate.val());
	
	var content = computeNewsContentUpdate();
	if(hasContent(content)){
		newsEntry.content = content;
	}
	var image = computeNewsImageUpdate();
	if(hasImage(image) || hasImageMetaData(image)){
		newsEntry.image  = image;
	}
	return newsEntry;
	
}


function computeNewsContentUpdate(){
	var content = {};
	
	var inputContent = $("#inputContent");
	var inputId = $("#entryId");
	var inputCreationDate = $("#entryCreationDate");
	var inputModificationDate = $("#entryModificationDate");
	
	content.content = inputContent.val();
	content.id = inputId.val();
	content.creationDate = formatDate(inputCreationDate.val());
	content.modificationDate = formatDate(inputModificationDate.val());
	
	return content;
}

function computeNewsImageUpdate(){
	var image = {};
	
	var inputLegend = $("#inputLegend");
	var inputAlt = $("#inputAlt");
	var inputId = $("#imageId");
	var inputCreationDate = $("#imageCreationDate");
	var inputModificationDate = $("#imageModificationDate");
	
	image.src= base64Image;
	image.alt = inputAlt.val();
	image.legend = inputLegend.val();
	image.id = inputId.val();
	image.creationDate = formatDate(inputCreationDate.val());
	image.modificationDate = formatDate(inputModificationDate.val());
	
	return image;
}

function formatDate(date){
	return new Date(date);
}