jQuery(function($) {
    $("#option1").click(function() {
        $("#required_name").prop("required", true);
        $("#required_name").prop("disabled", false);

        $("#required_role").prop("required", false);
        $("#required_role").prop("disabled", true);

        $("#required_name").focus();
    });
    $("#option2").click(function() {
        $("#required_name").prop("required", false);
        $("#required_name").prop("disabled", true);

        $("#required_role").prop("required", true);
        $("#required_role").prop("disabled", false);

        $("#required_role").focus();
    });
    $("#option3").click(function() {
        $("#required_name").prop("required", true);
        $("#required_name").prop("disabled", false);

        $("#required_role").prop("required", false);
        $("#required_role").prop("disabled", true);

        $("#required_name").focus();
    });
});