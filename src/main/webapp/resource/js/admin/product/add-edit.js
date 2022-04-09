var numOfImage = 1;
var MAX_FILE_SIZE = 512000;
var mode = "";
$(function () {

    $("#shorDescription").richText();
    $("#fullDescription").richText();
    if (document.getElementById("productId") != null) {
        mode = "edit";
    }


})
$(document).on("change", ".file_inp", loadImg)



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

function loadImg() {
    const file = this.files[0];
    if (!checkFileSize(this, 512000)) {
        return;
    }

    let previewContainer = this.previousSibling.previousSibling;

    console.log(previewContainer);
    let previewImage = previewContainer.querySelector(
        ".image-preview__img"
    );


    if (file) {
        const reader = new FileReader();

        reader.addEventListener("load", function () {
            previewImage.setAttribute("src", this.result);
        });
        reader.readAsDataURL(file);

        //on reupload existed image
        if (previewContainer.className.includes("saved-image-preview") && mode === "edit") {
            // alert(previewContainer.parentElement);
            // alert(previewContainer.parentElement.id);
            // console.log(previewContainer.parentElement);

            document.getElementById("delImageIds").value += previewContainer.parentElement.id + " ";
        }
        // createNewEmptyExtraImage();
    } else {
        previewImage.setAttribute("src", "");
    }

}


$(document).on("click", "#addNewImageExtraBtn", createNewEmptyExtraImage);


$(document).on("click", ".image-preview .close-i", function () {
    // alert(productId);
    if ($(this).parent().hasClass("saved-image-preview") && mode === "edit") {
        document.getElementById("delImageIds").value += this.parentElement.parentElement.id + " ";
    }

    $(this).parent().parent().hide();
})







