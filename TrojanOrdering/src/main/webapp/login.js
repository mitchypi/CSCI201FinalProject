function submitFunction(event) {   
	event.preventDefault(); 
    const email = $("#u-email").val();
    const password = $("#pword").val();

    $.ajax({
        url: "login",
        type: "POST",
        headers: { "Accept": "text/plain" },
        data: { 
            "email": email,
            "password": password,
        },
        success: function() {
            location.href = "home.html";
        },
        error: function(response) {
			alert(response.responseText);
        }
    });	
}