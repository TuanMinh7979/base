$(function () {

    $('.nav-tabs a').on('shown.bs.tab', function (event) {
        var editMode = $(event.target).text();

        if (editMode == "Detail") {

            callAttributeApi(`/admin/category/api/${categoryId}/attributes`, `/admin/category/api/${categoryId}/attributes/delete`);
            $("#addNewAttributeBtn").on("click", function (event) {
                event.preventDefault();
                $("#addModalBtn").click();
            })

            $("#saveAttributeBtn").on("click", function (event) {
                event.preventDefault();
                saveAttribute(this, `/admin/category/api/${categoryId}/attributes/add`);

            });
            $("#updateAttributeBtn").on("click", function (event) {
                event.preventDefault();
                saveAttribute(this, `/admin/category/api/${categoryId}/attributes/update`);

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
    loadAttributeToForm(this, `/admin/category/api/${categoryId}/attributes/value`);
    //after that click modal btn
    $("#editModalBtn").click();

})