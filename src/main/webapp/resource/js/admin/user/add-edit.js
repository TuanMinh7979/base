var mode = "";
var numOfImage = 1;
var MAX_FILE_SIZE = 512000;

$(function () {

    $("#roles-sel").select2();
    if (document.getElementById("userId-inp") != null) {
        mode = "edit";
        loadActiveRoleIds();
    }

    $("#selectnone-btn").on("click", function (event) {
        event.preventDefault();
        selectNoneImage(this);
    })


})


$(document).on("change", ".file_inp", loadFile)

function selectNoneImage(btn) {
    let imagePreview = $(btn).parent().find(".image-preview");
    let imagePreviewImg = imagePreview.find(".image-preview__img");


    imagePreview.parent().find(".file_inp").val(null);

    imagePreviewImg.attr("src", `${defaultImage}`)


    if (mode === "edit") {
        $("#delImageId").val(imagePreview.parent().attr("id"));
    }

}

function loadFile() {
    let file = this.files[0];

    let imagePreview = $(this).parent();

    let imagePreviewImg = imagePreview.find(".image-preview__img");
    if (!checkFileSize(file, this, MAX_FILE_SIZE)) {
        // alert(this.files);

        return;
    }

    if (file) {
        const reader = new FileReader();


        let readerJo = $(reader);
        readerJo.on("load", function () {
            imagePreviewImg.attr("src", this.result);
        })


        reader.readAsDataURL(file);


        if (imagePreview.find("saved-image-preview") != null && mode === "edit") {

            $("#delImageId").val(imagePreview.attr('id'));
        }

    } else {

        imagePreviewImg.attr("src", `${defaultImage}`)

    }

}


const loadActiveRoleIds = () => {
    let userId = $("#userId-inp").val();
    url = `/admin/user/api/${userId}/active-role-ids`;
    let roleSelectOptions = $('#roles-sel option');

    $.ajax({
        type: "get",
        url: url,
        contentType: "application/json",
        success: function (data) {
            let activeIdArr = [];
            data.map(function (idi) {

                    $.each(roleSelectOptions, function (index, value) {
                        if (value.value == idi) {
                            let opt = $(value);
                            activeIdArr.push(opt.attr('value'));
                            return;
                        }

                    })
                }
            )

            $("#roles-sel").val(activeIdArr).trigger('change');
        },
        error: function () {

        }


    })

}












