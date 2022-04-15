$(function () {

    callAttributeApi(`/admin/category/api/${categoryId}/attributes`);

})

function renderDataForAttributeTable(data) {


}

function callAttributeApi(url) {

    $.ajax({
        type: "get",
        url: url,
        success: function (data) {

            renderDataForAttributeTable(data);
        },
        error: function () {
            Swal.fire({
                icon: 'error',
                title: 'Can not call this Api',
                // text: 'Something went wrong!',

            })
        }


    });
}