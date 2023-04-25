function checkSessionCookie() {
  var cookies = document.cookie.split(';');
  for (var i = 0; i < cookies.length; i++) {
    var cookie = cookies[i].trim();
    if (cookie.startsWith("JSESSIONID=")) {
      return true;
    }
  }
  return false;
}

