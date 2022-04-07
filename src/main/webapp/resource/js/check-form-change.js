$("#mainForm").data("changed", false);

$("#mainForm").on("change", function () {
    $(this).data("changed", true);
});
$('#mainForm').on('submit', function () {
    if (!$(this).data("changed")) {
        alert("Nothing changed!");
        return false;
        // Reset variable
    }
    // Do whatever you want here
    // You don't have to prevent submission

});