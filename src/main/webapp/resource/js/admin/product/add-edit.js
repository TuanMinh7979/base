$(function () {
    $("#description-text").richText();

})
$(document).on("change", ".file_inp", loadImg)

function loadImg() {
    const file = this.files[0];

    let previewContainer = this.nextSibling.nextSibling;

    let previewImage = previewContainer.querySelector(
        ".image-preview__img"
    );

    let previewDefaultText = previewContainer.querySelector(
        ".image-preview__def-text"
    );

    if (file) {
        const reader = new FileReader();

        previewDefaultText.style.display = "none";
        previewImage.style.display = "block";

        reader.addEventListener("load", function () {

            previewImage.setAttribute("src", this.result);
        });
        reader.readAsDataURL(file);
        previewContainer.querySelector("input[type='checkbox']").style.display="block"
        previewContainer.querySelector(".close-i").style.display="none"
    }

}


$(document).on("click", "#addNewImageExtraBtn", function (event) {

        event.preventDefault();
        html = '      <div id="load-image-preview" class="col-6">\n' +
            '            <input type="file" name="image" class="file_inp"/>\n' +
            '            <div class="image-preview">\n' +
            '                <input type="checkbox" />\n' +
            '                <i class="close-i fas fa-times"></i>\n' +
            '                <img src="" alt="alt" class="image-preview__img"/>\n' +
            '                <span class="image-preview__def-text">Image Preview1</span>\n' +
            '\n' +
            '\n' +
            '            </div>\n' +
            '        </div>'

        $("#imageWrapper").append(html);
    }
);

$(document).on("click", ".image-preview", function () {
    let selectCheckbox = $(this).find("input[type='checkbox']");
    selectCheckbox.prop("checked", !selectCheckbox.prop("checked"));

})

$(document).on("click", ".image-preview input[type='checkbox']", function () {
    alert("asdkjfnsf");
    $(this).prop("checked", !$(this).prop("checked"));

})



$(document).on("click", ".image-preview .close-i", function () {

    $(this).parent().parent().hide();
})







