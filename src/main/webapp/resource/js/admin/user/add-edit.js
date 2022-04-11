
var mode = "";
var numOfImage = 1;
var MAX_FILE_SIZE = 512000;

$(function () {

    $("#roles-sel").select2();
    if (document.getElementById("userId") != null) {
        mode = "edit";
    }
})
$(document).on("change", ".file_inp", loadImg)


function loadImg() {
    const file = this.files[0];

    let previewContainer = this.previousSibling.previousSibling;
    let previewImage = previewContainer.querySelector(
        ".image-preview__img"
    );
    if (!checkFileSize(this, MAX_FILE_SIZE)) {
        return;
    }

    if (file) {
        const reader = new FileReader();

        reader.addEventListener("load", function () {
            previewImage.setAttribute("src", this.result);
        });
        reader.readAsDataURL(file);

        //on reupload existed image
        if (previewContainer.className.includes("saved-image-preview") && mode === "edit") {
            document.getElementById("delImageIds").value += previewContainer.parentElement.id + " ";
        }

    } else {
        previewImage.style.display = null;
        previewImage.setAttribute("src", `${defaultImage}`);
    }

}












