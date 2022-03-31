$(function () {
    $(".parent_permission_chbx").on("click", function () {
        $(this).parents(".card").find(".child_permission_chbx").prop("checked", $(this).prop("checked"))
    })
    $("#all-chbx").on("click", function () {

        $(".parent_permission_chbx").prop("checked", $(this).prop("checked"));
        $(".child_permission_chbx").prop("checked", $(this).prop("checked"));
    })
})