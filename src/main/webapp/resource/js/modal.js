const setTopModal = (topModalId, title, contentHtml) => {
    let modal = $("#" + topModalId);
    modal.find(".modal-title").text(title);
    modal.find(".modal-body").html(contentHtml);

}