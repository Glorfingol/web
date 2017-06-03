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
	
	var inputTitle = $("#title");
	var inputAuthor = $("#author");
	var inputTags = $("#tags");
	var inputId = $("#id");
	var inputCreationDate = $("#creationDate");
	var inputModificationDate = $("#modificationDate");
	
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
	
	
	content.content = inputContent.val();
	if(inputId.val()){
		content.id = inputId.val();	
	}
	if(inputCreationDate.val()){
		content.creationDate = formatDate(inputCreationDate.val());
	}
	if(inputModificationDate.val()){
		content.modificationDate = formatDate(inputModificationDate.val());
	}
	return content;
}

function computeNewsImageUpdate(){
	var image = {};
	
	
	image.src= base64Image;
	image.alt = inputAlt.val();
	image.legend = inputLegend.val();
	if(inputId.val()){
		image.id = inputId.val();	
	}
	if(inputCreationDate.val()){
		image.creationDate = formatDate(inputCreationDate.val());
	}
	if(inputModificationDate.val()){
		image.modificationDate = formatDate(inputModificationDate.val());
	}
	return image;
}

function formatDate(date){
	return new Date(date);
}