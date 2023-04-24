//https://www.codehim.com/collections/javascript-shopping-cart-examples-with-demo/

var taxRate = 0.05;
var shipping = 2.0;

 function cartItem(name, price, quantity) {
  this.name = name;
  this.price = price;
  this.quantity = quantity;
  this.total = price * quantity;
}



$(function() {
  //var jsonData = [
  var i = 0;
  const items = [];
    fetch('/checkout?userID=1234')
     .then(response => response.json())
     .then(data => {
        // process the JSON data here
        data.forEach(item => { 
         const cartItemTemp = new cartItem(item.name, item.price, 1);
         var itemExists = false;
         items.forEach(existingItem => {
            if (existingItem.name == cartItemTemp.name) {
             itemExists = true;
             existingItem.quantity++;
            }
         });
          if (!itemExists) {
            items.push(cartItemTemp);
         }
      });
     })
     .catch(error => console.error(error));
    // {
    //   title: "Item 1",
    //   price: 38,
    //   quantity: 1,
    //   total: 38
    // },
    // {
    //   title: "Item 2",
    //   price: 23.5,
    //   quantity: 1,
    //   total: 23.5
    // },
    // {
    //   title: "Item 3",
    //   price: 100,
    //   quantity: 1,
    //   total: 100
    // },
    // {
    //   title: "Item 4",
    //   price: 45,
    //   quantity: 1,
    //   total: 45
    // },
    // {
    //   title: "Item 5",
    //   price: 66,
    //   quantity: 1,
    //   total: 66
    // },
    // {
    //   title: "Item 6",
    //   price: 199,
    //   quantity: 1,
    //   total: 199
    // }
  ;
  var html = "<tbody>";
  $.each(items, function() {
    html +=
      '<tr class="cart-item">' +
      "        <td>" +
      '          <input type="checkbox" class="cart-item-check" checked />' +
      "        </td>" +
      "        <td>" +
      "          " +
      this.name +
      "        </td>" +
      "        <td>$" +
      this.price +
      "</td>" +
      "        <td>" +
      '          <input class="input is-primary cart-item-qty" style="width:100px" type="number" min="1" value="' +
      this.quantity +
      '" data-price="' +
      this.price +
      '">' +
      "        </td>" +
      '        <td class="cart-item-total">$' +
      this.total +
      "</td>" +
      "        <td>" +
      '          <a class="button is-small">Remove</a>' +
      "        </td>" +
      "      </tr>";
  //]
  });
  html += "</tbody>";
  $(".shopping-cart").append(html);
  
  recalculateCart();

  $(".cart-item-check").change(function() {
    recalculateCart();
  });

  $(".cart-item-qty").change(function() {
    var $this = $(this);
    var parent = $this.parent().parent();
    parent.find(".cart-item-check").prop("checked", "checked");
    var price = $this.attr("data-price");
    var quantity = $this.val();
    var total = price * quantity;
    parent.find(".cart-item-total").html(total.toFixed(2));
    recalculateCart();
  });

  $("#empty-cart").click(function() {
    items.length = 0; // Clear the items array
    html = "<tbody></tbody>"; // Empty the HTML cart list
    $(".shopping-cart").html(html); // Update the HTML cart list
    recalculateCart(); // Recalculate the cart totals
  });

  $(".button").click(function() {
    var parent = $(this)
      .parent()
      .parent();
    parent.remove();
    recalculateCart();
  });
});
function recalculateCart() {
  var subTotal = 0;
  var grandTotal = 0;
  var tax = 0;
  var items = $(".cart-item");
  $.each(items, function() {
    var itemCheck = $(this).find(".cart-item-check");
    var itemQuantity = $(this).find(".cart-item-qty");
    if (itemCheck.prop("checked")) {
      var itemTotal = itemQuantity.val() * itemQuantity.attr("data-price");
      subTotal += itemTotal;
    }
  });
  if (subTotal > 0) {
    tax = subTotal * taxRate;
    grandTotal = subTotal + tax + shipping;
    $(".totals,.checkout").show();
  } else {
    $(".totals,.checkout").hide();
  }
  $("#cart-subtotal").html(subTotal.toFixed(2));
  $("#cart-total").html(grandTotal.toFixed(2));
  $("#cart-tax").html(tax.toFixed(2));
  $("#cart-shipping").html(shipping.toFixed(2));
}