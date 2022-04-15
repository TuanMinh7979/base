$(function () {

    callAttributeApi(`/admin/category/api/${categoryId}/attributes`);
    $("#addNewAttributeBtn").on("click", function (event) {
        event.preventDefault();
        $("#addModalBtn").click();
    })

    $("#saveAttributeBtn").on("click", function (event) {
        event.preventDefault();
        saveAttribute(this);

    });

    $("#editAttributeBtn").on("click", function(event){
        event.preventDefault();
        $("#editModalBtn").click();
    })

})

function saveAttribute(saveBtn) {


    data = {}
    let modelContent = $(saveBtn).parent().parent();
    let attributeWrapper = modelContent.find("#attributeWrapper");

    data["name"] = attributeWrapper.find("#atrNameInp").val();
    data["active"] = attributeWrapper.find("#atrActiveChbx").is(":checked") ? 1 : 0;
    let valueArr = [];
    attributeWrapper.find(".atrValueSpan").each(function () {
        valueArr.push(this.innerText);
    })
    data["value"] = valueArr;


    $.ajax({
        url: `/admin/category/api/${categoryId}/attributes/add`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (result) {
            alert(result);
        },
        error: function (error) {
            window.location.href = "/";
        }
    });

}

function renderDataForAttributeTable(data) {
    let rs = "";

    data.map(function (atbi) {


        rs += `       <tr>
                <td class="col-1"><input type="checkbox"></td>
                <td class="col-5" >${atbi.name}</td>
                <td class="col-3"><input type="checkbox" checked="${atbi.active}"></td>
                <td class="col-3">
                    <button>EDIT</button>
                    <button>DELETE</button>
                </td>

            </tr>`
    })
    $("#tabledata").html(rs);


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

document.querySelector('#push').onclick = function (event) {
    event.preventDefault();
    if (document.querySelector('#newtask input').value.length == 0) {
        alert("Please Enter a Task")
    } else {
        document.querySelector('#tasks').innerHTML += `
            <div class="task">
                <span class="atrValueSpan"  id="taskname">
                    ${document.querySelector('#newtask input').value}
                </span>
                <button class="delete">
                    <i class="far fa-trash-alt"></i>
                </button>
            </div>
        `;

        var current_tasks = document.querySelectorAll(".delete");
        for (var i = 0; i < current_tasks.length; i++) {
            current_tasks[i].onclick = function () {
                this.parentNode.remove();
            }
        }


    }
}


