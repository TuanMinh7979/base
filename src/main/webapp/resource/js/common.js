function deleteOnTable(event) {
    event.preventDefault();
    let url = $(this).attr("href");
    let that = $(this);
    Swal.fire({

        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.value) {
            $.ajax({
                type: "POST",
                url: url,
                success: function (data) {
                    that.closest("tr").remove();
                },
                error: function (data) {


                    // console.log(data.responseJSON);
                    Swal.fire({
                        icon: 'error',
                        title: 'Can not delete it',
                        // text: 'Something went wrong!',

                    })
                }


            })
        }
    })
}

function deleteManyOnTable(event) {
    event.preventDefault();
    let url = $(this).attr("href");
    // let that = $(this);

    var toDelChecboxs = [];
    var checkboxes = document.querySelectorAll('tr input[type="checkbox"]:checked');


    for (var i = 0; i < checkboxes.length; i++) {
        toDelChecboxs.push(parseInt(checkboxes[i].value))
    }

    Swal.fire({

        title: 'Are you sure to delete these?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.value) {

            $.ajax({
                type: "post",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(toDelChecboxs),


                success: function (data) {
                    for (checkbox of checkboxes) {
                        checkbox.parentElement.parentElement.remove();

                    }


                },
                error: function (data) {

                    Swal.fire({
                        icon: 'error',
                        title: 'Can not delete',
                        // text: 'Something went wrong!',

                    })
                }


            })
        }
    })
}

function checkFileSize(file, fileInp, maxsize) {
    let maxsizeInKb= maxsize/1024;
    if (file!=null && file.size > maxsize) {

        fileInp.setCustomValidity("Image must less than "+maxsizeInKb+" Kb");
        fileInp.reportValidity();
        return false;
    } else {
        return true;
    }
}










