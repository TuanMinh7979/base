var mode = "";

//FOR IMAGES SCREEN
var numOfImage = 1;
var MAX_FILE_SIZE = 512000;

$(function () {

    $("#shorDescription").richText();
    $("#fullDescription").richText();
    if (document.getElementById("productId") != null) {
        mode = "edit";
    }

    $("#selectnone-btn").on("click",
        function (event) {
            event.preventDefault();
            handleSelectDefaultBtn(this, mode, "delImageIds", defaultImage);
        })
})


$(document).on("change", ".file_inp", function (event) {
    event.preventDefault();
    changeImage(this.files[0], this, mode, "delImageIds", defaultImage)

});




function createNewEmptyExtraImage() {


    event.preventDefault();
    let html = '       <div class="col-6">\n' +
        '\n' +
        '<span>Extra</span>' +
        '            <div class="image-preview">\n' +
        '                <i class="close-i fas fa-times"></i>\n' +
        `                <img  src="${defaultImage}" alt="alt" class="image-preview__img"/>\n` +
        '\n' +
        '\n' +
        '            </div>\n' +
        '            <input type="file" name="files" class="file_inp"/>\n' +
        '        </div>'


    $("#imageWrapper").append(html);


}


$(document).on("click", "#addNewImageExtraBtn", createNewEmptyExtraImage);


$(document).on("click", ".image-preview .close-i", function () {
    // alert(productId);
    if ($(this).parent().hasClass("saved-image-preview") && mode === "edit") {
        document.getElementById("delImageIds").value += this.parentElement.parentElement.id + " ";
    }

    $(this).parent().parent().hide();
})

//DETAIL ATTRIBUTE TAB
$(function () {

    $('.nav-tabs a').on('shown.bs.tab', function (event) {
        var editMode = $(event.target).text();

        if (editMode == "Detail") {

            callAttributeApi(`/admin/product/api/${productId}/attributes`, `/admin/product/api/${productId}/attributes/delete`);
            $("#addNewAttributeBtn").on("click", function (event) {
                event.preventDefault();
                $("#addModalBtn").click();
            })

            $("#saveAttributeBtn").on("click", function (event) {
                event.preventDefault();
                saveAttribute(this, `/admin/product/api/${productId}/attributes/add`);

            });
            $("#updateAttributeBtn").on("click", function (event) {
                event.preventDefault();
                saveAttribute(this, `/admin/product/api/${productId}/attributes/update`);

            });
            $("#tag_delete_many").on("click", deleteManyOnTableByStringIdArray);

            $(document).on("click", '.tag_delete_one', function (event) {
                event.preventDefault();
                deleteOnTableWithStringIdData(this, $(this).parent().parent().find(".atb-iddel-checkbox").val());
            })


        }


    });

})

$(document).on("click", ".push", function (event) {

    event.preventDefault();
    pushNewRecord(this)


});
$(document).on("click", ".delete", function (event) {

    event.preventDefault();
    $(this).parent().remove();
});



$(document).on("click", ".editAttributeBtn", function (event) {
    event.preventDefault();
    //load data first
    loadAttributeToForm(this, `/admin/product/api/${productId}/attributes/value`);
    //after that click modal btn
    $("#editModalBtn").click();

})
//-DETAIL ATTRIBUTE TAB










