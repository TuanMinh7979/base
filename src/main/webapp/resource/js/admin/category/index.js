// console.log(totalPage+" "+pageNumber);

$(function () {
    $(".tag_delete_one").on("click", deleteOnTable);
    $("#tag_delete_many").on("click", deleteManyOnTable)

    upSelect("selBy", "sortBy", "id")
    upSelect("selDirection", "sortDirection", "asc")


})

function loadData() {
    let sortBy = $("#sortBy").val();
    let sortDescrription = $("#sortDescription").val();

    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);


    let page = urlParams.get("page");
    let limit = urlParams.get("limit");




}



