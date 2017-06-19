var postsToImport = [];
var checkBoxSelector = "input[type='checkbox']";
$(document).ready(function(){
	$(checkBoxSelector).each(function(){
		if(!isImportAllCheckBox(this.id)){
			$(this).change(function(){
				var fullId = this.id;
				var facebookId = fullId.split('-')[1];
				toggleImport(facebookId);
			});
		}
		if(isImportAllCheckBox(this.id)){
			$(this).change(function(){
				toggleAllImport();
			});
		}
	});
})


function toggleAllImport(){
	$(checkBoxSelector).each(function(){
		if(!isImportAllCheckBox(this.id)){
			var fullId = this.id;
			var facebookId = fullId.split('-')[1];
			if(isAllImportChecked()){
				if(!computeIsToImport(facebookId)){
					$(this).prop("checked", true);
					toggleImport(facebookId);
				}
			}else{
				if(computeIsToImport(facebookId)){
					$(this).prop("checked", false);
					toggleImport(facebookId);
				}
			}
		}
	});
}

function toggleImport(facebookId){
	
	var isToImport = computeIsToImport(facebookId);
	if(isToImport){
		var post = computePostToImport(facebookId);
		if(!isAlreadyInImport(facebookId)){
			postsToImport.push(post);
		}
	}else{
		postsToImport.forEach(function(post){
			if(post.facebookId === facebookId){
				var index = postsToImport.indexOf(post);
				if(index !== -1){
					postsToImport.splice(index, 1);
				}
			}
		});
	}
}

function isAlreadyInImport(facebookId){
	var isAlreadyInImport = false;
	postsToImport.forEach(function(post){
		if(post.facebookId === facebookId){
			isAlreadyInImport = true;
		}
	});
	return isAlreadyInImport;
}

function isImportAllCheckBox(checkboxId){
	return checkboxId === 'importAll';
}

function isAllImportChecked(){
	var checkboxId = '#importAll';
	var checkboxInput = $(checkboxId);
	return checkboxInput.is(":checked");
}

function computeIsToImport(facebookId){
	var checkboxId = '#checkbox-' + facebookId;
	var checkboxInput = $(checkboxId);
	return checkboxInput.is(":checked");
}

function computePostToImport(facebookId){
	var postToImport = {};
	postToImport.facebookId = facebookId;
	return postToImport;
}