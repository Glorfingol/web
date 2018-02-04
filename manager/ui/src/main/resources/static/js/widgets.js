function validateAndCreateWidget() {
    var widget = computeWidget();
    return widget;
}

function cancelUpdateWidget() {
    window.location.href = "/manager/widgets";
}

function validateAndUpdateWidget() {
    var widget = computeUpdateWidget();
    return widget;
}


function computeWidget() {
    var widget = {};

    var inputName = $("#name");
    var inputType = $("#type");

    widget.name = inputName.val();
    widget.type = inputType.val();

    return widget;

}

function computeUpdateWidget() {
    var widget = {};

    var inputName = $("#name");
    var inputType = $("#type");
    var inputEntityId = $("#entityId");
    var inputId = $("#id");
    var inputCreationDate = $("#creationDate");
    var inputModificationDate = $("#modificationDate");

    widget.name = inputName.val();
    widget.type = inputType.val();
    widget.entityId = inputEntityId.val();
    widget.personalization = computePersonalization();
    widget.id = inputId.val();
    widget.creationDate = inputCreationDate.val();
    widget.modificationDate = inputModificationDate.val();
    return widget;
}

function computePersonalization() {
    if (CKEDITOR.instances.widgetPersonalization) {
        return CKEDITOR.instances.widgetPersonalization.getData();
    }
    return $("#widgetPersonalization").val();
}


function postCreateWidgetForm() {
    var url = "/manager/widgets";
    create($("#widgetCreateForm"), $(".loader"), $(".card-loader"), url, url, validateAndCreateWidget());
}


function postUpdateWidgetForm() {
    var widgetToUpdate = validateAndUpdateWidget();
    var url = "/manager/widgets/" + widgetToUpdate.id;
    var urlFallback = "/manager/widgets/" + widgetToUpdate.id;
    update($("#widgetUpdateForm"), $(".loader"), $(".card-loader"), url, urlFallback, widgetToUpdate, true);
}

