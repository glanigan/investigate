$(document).ready(function () {

    $("#riskScoreCheckbox").bind("click", function (event) {
        $("#riskScoreValue").toggle("disabled");
    });
});


function validateForm(){
    if(document.getElementById("riskScoreCheckbox").checked===true && $("#riskScoreValue").val()<=0){
        alert("Disable 'Enable Rule Score Decisions' or enter a risk score greater than 0.");
        return false;
    }
}
