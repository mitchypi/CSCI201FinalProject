function submitFunction(event) {   
	event.preventDefault(); 
    const username = $("#username").val();
    const password = $("#password").val();

    $.ajax({
        url: "login",
        type: "POST",
        headers: { "Accept": "text/plain" },
        data: { 
            "username": username,
            "password": password,
        },
        success: function() {
            location.href = "home.html";
            loginUser();
        },
        error: function(response) {
			alert(response.responseText);
        }
    });	
}