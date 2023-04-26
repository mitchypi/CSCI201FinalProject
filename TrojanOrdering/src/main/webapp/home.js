function grabRestaurants() {
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

      // get all the <a> elements on the page that do not have the "nav-exclude" class
      const aElements = document.querySelectorAll('a:not(.nav-exclude)');

      // loop through each <a> element
      for (let i = 0; i < aElements.length; i++) {
        // add a click event listener to each <a> element
        console.log(`Adding click event listener to <a> element with ID ${aElements[i].getAttribute('id')}.`);
        aElements[i].addEventListener('click', function(event) {
          // prevent the default behavior of clicking on a link (i.e. redirecting to a new page)
          event.preventDefault();
          
          // get the id attribute of the clicked <a> element
          const id = this.getAttribute('id');
          
          // make a request to the RestIDServlet to set the session's restaurant_id attribute
          let url = `RestIDServlet?id=${id}`;
          fetch(url)
            .then(response => {
              // redirect to the menu page
              window.location.href = 'menu.html';
            })
            .catch(error => console.error(error));
        });
      }
    })
    .catch(error => console.error(error));
}