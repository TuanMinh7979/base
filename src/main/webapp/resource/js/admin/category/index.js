function callViewApi(page, limit, sortBy, sortDirection) {

    let url = "/admin/category/viewApi";
    if (page == null) {
        url += `?page=1`;
    } else {
        url += `?page=${page}`;
    }
    if (limit != null) {
        url += `&limit=${limit}`;
    }
    if (sortBy != null) {
        url += `&sortBy=${sortBy}`;
    }
    if (sortDirection != null) {
        url += `&sortDirection=${sortDirection}`;
    }
    console.log("URL la " + url);
    $.ajax({
        type: "get",
        url: url,
        success: function (data) {
            renderData(data);
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

$(function () {
    alert("vao ham load ban dau")

    callViewApi(null, null, null, null);
    paging();



})


function HdleFilterBtn() {
    //load data sử dụng bộ filter
    let sortBy = document.getElementById("by-sel").value;
    let sortDirection = document.getElementById("direction-sel").value;
    let limit = document.getElementById("limit-inp").value;
    callViewApi(null, limit, sortBy, sortDirection);


}

function renderData(data) {
    let arrCate = [];
    arrCate = data.data;


    let trcontent = "";
    for (let i = 0; i < arrCate.length; i++) {
        trcontent += `<tr> <td class="col-1"><input type="checkbox" value="${arrCate[i].id}"></td>
 <td class="col-4" >${arrCate[i].name}</td>
  <td class="col-4" >${arrCate[i].code}</td>
 <td class="col-3">
 <a class="btn btn-default"  href="/admin/category/edit/${arrCate[i].id}">Edit</a>
  <a class="btn btn-danger tag_delete_one"  onclick="deleteOnTable(event)" href="/admin/category/delete/${arrCate[i].id}">Delete</a>
</td>
 </tr>`;

    }


    $("#tabledata").html(trcontent);
}

$(function () {

    //doi voi preventDefault nhu the a nên đặt onclick vì khi html render bằng ajax event bằng class hay id sẽ KHÔNG đc

    $("#tag_delete_many").on("click", deleteManyOnTable);
    $("#btn-filter").on("click", HdleFilterBtn);


})








