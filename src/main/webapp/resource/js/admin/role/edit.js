$(function () {

    loadActivePermissionIds();


})

const loadActivePermissionIds = () => {
    let roleId = $("#id-inp").val();
    url = `/admin/role/api/active-permission-ids/${roleId}`;
    let checkboxes = $('.card-body input[type=checkbox]');

    $.ajax({
        type: "get",
        url: url,
        contentType: "application/json",


        success: function (data) {

            data.map(function (idi) {

                    $.each(checkboxes, function (index, value) {
                        if (value.value == idi) {
                            var jelm = $(value);
                            jelm.prop("checked", true);
                        }

                    })
                }
            )
        },
        error: function () {

        }


    })

}

$(function () {
    $(".parent_permission_chbx").on("click", function () {
        $(this).parents(".card").find(".child_permission_chbx").prop("checked", $(this).prop("checked"))
    })
    $("#all-chbx").on("click", function () {

        $(".parent_permission_chbx").prop("checked", $(this).prop("checked"));
        $(".child_permission_chbx").prop("checked", $(this).prop("checked"));
    })
})
