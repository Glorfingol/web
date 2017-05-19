var postsToImport = [];
$(document).ready(function(){
	$("input[type='checkbox']").each(function(){
		if(this.id !== 'importAll'){
			$(this).change(function(){
				var fullId = this.id;
				var facebookId = fullId.split('-')[1];
				toggleImport(facebookId);
			})
		}
	})
})




function toggleImport(facebookId){
	
	var isToImport = computeIsToImport(facebookId);
	if(isToImport){
		postsToImport.push(computePostToImport(facebookId));
	}else{
		postsToImport.forEach(function(post){
			if(post.facebookId === facebookId){
				var index = postsToImport.indexOf(post);
				if(index !== -1){
					postsToImport.splice(index, 1);
				}
			}
		})
	}
	
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