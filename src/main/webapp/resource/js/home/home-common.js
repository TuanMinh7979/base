function createSwiper(selector) {
    let swiper = new Swiper(selector, {
        slidesPerView: 1,
        spaceBetween: 30,
        slidesPerGroup: 1,
        loop: true,
        loopFillGroupWithBlank: true,
        pagination: {
            // el: ".swiper-pagination",
            clickable: true,
        },
        lazyLoading: true,
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
    });
    return swiper;
}

//load content through ajax
function simpleLoadContentWithGet(url, renderMethod) {

    $.ajax({
        type: "get",
        url: url,
        success: function (data) {
            if (typeof renderMethod === "function") {
                renderMethod(data);
            }

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

//load content through ajax