function grabRestaurants(){
    let url = "HomeServlet";
    fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        },
      })
      .then(response => response.text())
      .then(html => {
        // Replace the contents of a div with the HTML response
        document.getElementById('restaurants').innerHTML = html;
      })
      .catch(error => console.error(error));
    //print out alert of what server prints
}
