function cancelUpdateGroup() {
  window.location.href = "/manager/groups";
}

function cancelCreateGroup() {
  window.location.href = "/manager/groups";
}

function validateAndUpdateGroup() {
  var group = computeGroupToUpdate();
  return group;
}

function validateAndCreateGroup() {
  var group = computeGroupToCreate();
  return group;
}

function computeGroupToUpdate() {
  var group = {};

  var inputName = $("#name");
  var inputId = $("#id");
  var inputCreationDate = $("#creationDate");
  var inputModificationDate = $("#modificationDate");
  var inputCreationUser = $("#creationUser");
  var inputModificationUser = $("#modificationUser");

  group.name = inputName.val();
  group.description = computeGroupDescription();

  group.id = inputId.val();
  group.creationDate = formatDate(inputCreationDate.val());
  group.modificationDate = formatDate(inputModificationDate.val());
  group.creationUser = inputCreationUser.val();
  group.modificationUser = inputModificationUser.val();

  return group;

}

function computeGroupToCreate() {
  var group = {};

  var inputName = $("#name");

  group.name = inputName.val();
  group.description = computeGroupDescription();

  return group;

}

function computeGroupDescription() {
  var description = "";
  description = CKEDITOR.instances.description.getData();
  return description;
}

function postUpdateGroupForm() {
  var groupToUpdate = validateAndUpdateGroup();
  var url = "/manager/groups/" + groupToUpdate.id;
  var urlFallback = "/manager/groups/" + groupToUpdate.id;
  update($("#groupUpdateForm"), $(".loader"), $(".card-loader"), url,
      urlFallback,
      groupToUpdate).done(function (data) {
    handleSuccessPutResult(data, $(".card-loader"), $(".loader"),
        $("#groupUpdateForm"), url, true);
    currentTab = "";
    goToGroupMainTab();
  }).fail(function (error) {
    handleErrorPutResult(urlFallback);
  });
}

function postCreateGroupForm() {
  var groupToCreate = validateAndCreateGroup();
  var url = "/manager/groups/";
  var urlFallback = "/manager/groups/";
  create($("#groupCreateForm"), $(".loader"), $(".card-loader"), url,
      urlFallback, groupToCreate).done(function (data) {
    handleSuccessPostResult(data, $(".card-loader"), $(".loader"),
        $("#groupCreateForm"), url);
  }).fail(function (error) {
    handleErrorPostResult(urlFallback);
  });
}
