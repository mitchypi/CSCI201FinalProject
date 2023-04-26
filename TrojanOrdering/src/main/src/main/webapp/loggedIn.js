let isLoggedIn = JSON.parse(sessionStorage.getItem('isLoggedIn'));

function loginUser() {
    sessionStorage.setItem('isLoggedIn', JSON.stringify(true));
}

function logoutUser() {
    sessionStorage.setItem('isLoggedIn', JSON.stringify(false));
}

function loggedIn(){
	return isLoggedIn;
}