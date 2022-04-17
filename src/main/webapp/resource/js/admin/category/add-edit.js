$(function () {

    $('.nav-tabs a').on('shown.bs.tab', function (event) {
        var editMode = $(event.target).text();

        if (editMode == "Detail") {

            callAttributeApi(`/admin/category/api/${categoryId}/attributes`);
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


$(document).on("click", ".editAttributeBtn", function (event) {
    event.preventDefault();
    //load data first
    loadAttributeToForm(this);
    //after that click modal btn
    $("#editModalBtn").click();

})

function loadAttributeToForm(editAttributeBtn) {
    let atbTr = $(editAttributeBtn).parent().parent();
    let atbId = atbTr.find(".atb-iddel-checkbox").val();
    let atbName = atbTr.find("td:nth-child(2)").text();


    let editModal = $("#exampleModalCenter2");
    editModal.find("#atrIdInp").val(atbId);
    editModal.find("#atrNameInp").val(atbName);
    let atbActive = atbTr.find(".atb-active-checkbox");
    let isActive = atbActive.attr("isActive") == 1 ? true : false;


    editModal.find("#atrActiveChbx").prop("checked", isActive);
    let data = {}
    data["id"] = atbId;


    let valueList = editModal.find(".tasks");
    $.ajax({
        url: `/admin/category/api/${categoryId}/attributes/value`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (data) {
            let rs = "";

            data.map(function (valuei) {
                rs +=
                    `<div class="task">
                <span class="atrValueSpan" class="taskname">
                    ${valuei}
                </span>
                        <button class="delete">
                            <i class="far fa-trash-alt"></i>
                        </button>
                    </div>`

            })
            valueList.html(rs);


        },
        error: function (error) {
            console.log(error)
            // window.location.href = "/";
        }
    });


}


function saveAttribute(saveBtn, url) {


    let data = {}
    let modelContent = $(saveBtn).parent().parent();
    let attributeWrapper = modelContent.find(".attributeWrapper");

    if (attributeWrapper.find("#atrIdInp") != null) {
        data["id"] = attributeWrapper.find("#atrIdInp").val();
    }
    data["name"] = attributeWrapper.find("#atrNameInp").val();
    data["active"] = attributeWrapper.find("#atrActiveChbx").is(":checked") ? 1 : 0;
    let valueArr = [];
    attributeWrapper.find(".atrValueSpan").each(function () {
        valueArr.push($(this).text().replace(/\r?\n|\r/g, " ").trim());
    })
    data["value"] = valueArr;


    $.ajax({
        url: url,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (result) {
            location.reload();
        },
        error: function (error) {
            console.log(error);
        }
    });

}

function renderDataForAttributeTable(data) {
    let rs = "";


    data.map(function (atbi) {


        rs += `       <tr>
                <td class="col-1"><input type="checkbox" class="atb-iddel-checkbox" value="${atbi.id}"></td>
                <td class="col-5" class="atb-name-inp" ">${atbi.name}</td>
                <td class="col-3"><i class="atb-active-checkbox fas fa-circle" isActive="${atbi.active}"></i>  </td>
                <td class="col-3">
                    <button class="editAttributeBtn btn btn-default" >Edit</button>
                    <a class="btn btn-danger tag_delete_one"  href="/admin/category/api/${categoryId}/attributes/delete" >Delete</a>

                </td>

            </tr>`
    })
    // }

    $("#tabledata").html(rs);
    setActiveCheckbox();


}

function setActiveCheckbox() {
    $(".atb-active-checkbox").each(function () {

        let isActive = $(this).attr("isActive") == 1 ? true : false;

        if(isActive ==1){
            $(this).css("color", "blue");
        }else{
            $(this).css("color", "red");
        }


    })
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

// document.querySelector('#push').onclick = function (event) {
$(document).on("click", ".push", function (event) {

    event.preventDefault();
    pushNewRecord(this)


});
$(document).on("click", ".delete", function (event) {

    event.preventDefault();
    $(this).parent().remove();
});

function pushNewRecord(pushAtrBtn) {
    let newtask = $(pushAtrBtn).parent();
    let newtaskInput = $(newtask).find("input");
    if (newtaskInput.val().length == 0) {

        alert("Please Enter a Task");
    } else {
        let tasksWrapper = newtask.parent().find(".tasks");
        let rs = "";
        rs += `
            <div class="task">
                <span class="atrValueSpan" class="taskname">
                   ${newtaskInput.val()}
                </span>
                <button class="delete">
                    <i class="far fa-trash-alt"></i>
                </button>
            </div>
        `;
        tasksWrapper.append(rs);

    }
}






