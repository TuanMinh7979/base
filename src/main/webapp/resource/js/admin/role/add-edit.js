var mode="";
$(function () {

    if (document.getElementById("id-inp") != null) {
        mode = "edit";
        loadActivePermissionIds();
    }


})

const loadActivePermissionIds = () => {
    let roleId = $("#id-inp").val();
    url = `/admin/role/api/${roleId}/active-permission-ids`;
    let checkboxes = $('.card-body input[type=checkbox]');

    $.ajax({
        type: "get",
        url: url,
        contentType: "application/json",


        success: function (data) {
            console.log(data);

            data.map(function (idi) {

                    $.each(checkboxes, function (index, value) {
                        if (value.value == idi) {
                            jelm = $(value);
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
