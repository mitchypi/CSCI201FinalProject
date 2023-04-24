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

function submitFunctionR(event) {
	event.preventDefault(); 	
    const password = $("#pwordR").val();
    const email = $("#u-emailR").val();
    const confirmPassword = $("#pwordRC").val();

    $.ajax({
        url: "register",
        type: "POST",
        headers: { "Accept": "text/plain" },
        data: { 
            email: email,
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

