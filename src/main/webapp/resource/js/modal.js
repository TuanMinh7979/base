const setTopModal = (topModelId, title, contentHtml) => {
    let modal = $("#" + topModelId);
    modal.find(".modal-title").text(title);
    modal.find(".modal-body").html(contentHtml);

}