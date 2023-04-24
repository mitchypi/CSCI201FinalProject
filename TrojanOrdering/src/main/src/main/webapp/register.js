function submitFunction(event) {
	event.preventDefault(); 	
    const username = $("#new-username").val();
    const password = $("#new-password").val();
    const email = $("#email").val();
    const confirmPassword = $("#confirm-password").val();

    $.ajax({
        url: "register",
        type: "POST",
        headers: { "Accept": "text/plain" },
        data: { 
            email: email,
            "new-username": username,
            "new-password": password,
            "confirm-password": confirmPassword
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