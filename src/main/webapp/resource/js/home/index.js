$(function () {

    simpleLoadContentWithGet("/admin/category/api/", loadCategoriesForMenu_Rd);
    simpleLoadContentWithGet("/admin/product/api/21", loadProductFor);

})


function loadCategoriesForMenu_Rd(data) {
    let container = $("#navBar").find(".depart-hover");
    let rs = "";
    data.map(function (datai) {
        rs += `<li><a href="${datai.code}">${datai.name}</a></li>`
    })
    container.html(rs);

}

// function loadProducts_Rd(data) {
//     alert(data);
//
// }
function loadProductFor(data){
    let container = $("#hotsaleSwi").find(".swiper-wrapper");
    let rs = "";
    data.map(function (datai) {
        rs += `<div class="swiper-slide"><img src="${datai.mainImageLink}" alt=""></div>`
    })
    container.html(rs);
    createSwiper("#hotsaleSwi")
}





