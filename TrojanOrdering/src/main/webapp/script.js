//https://www.codehim.com/collections/javascript-shopping-cart-examples-with-demo/

var taxRate = 0.05;
var shipping = 2.0;

class cartItem {
  constructor(name, price, quantity) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.total = price * quantity;
  }
}



$(function() {
  var userID, balance;
  $.ajax({
    url: '/TrojanOrdering/user_id',
    type: 'GET',
    dataType: 'text',
    success: function(data) {
        var lines = data.split('\n');
        userID = lines[0];
        balance = lines[1];
    },
    error: function(xhr, status, error) {
        console.log('Error:', error);
    }
  });
  //var jsonData = [
  //var userID = document.getElementById("user_id").value;
  var i = 0;
  const items = [];
    fetch('/TrojanOrdering/checkout?userID=' + userID)
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
    //   name: "Item 1",
    //   price: 38,
    //   quantity: 1,
    //   total: 38
    // },
    // {
    //   name: "Item 2",
    //   price: 23.5,
    //   quantity: 1,
    //   total: 23.5
    // },
    // {
    //   name: "Item 3",
    //   price: 100,
    //   quantity: 1,
    //   total: 100
    // },
    // {
    //   name: "Item 4",
    //   price: 45,
    //   quantity: 1,
    //   total: 45
    // },
    // {
    //   name: "Item 5",
    //   price: 66,
    //   quantity: 1,
    //   total: 66
    // },
    // {
    //   name: "Item 6",
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

  $("#emptycart").click(function() {
    items.length = 0; // Clear the items array
    html = "<tbody></tbody>"; 
    $(".shopping-cart").html(html); 
    recalculateCart(); 
  }); //found example of something similar online

  $("#submit-order").click(function() {
    //var balance = document.getElementById("balance").value;
    var orderTotal = 0;

    for (var i = 0; i < items.length; i++) {
      orderTotal += items[i].total;
    }
  
    if (balance >= orderTotal) {
      balance -= orderTotal;
      alert('Order completed');
    } else {
      alert('Not enough funds');
    }
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
    alert('Cart empty, please add items');
    $(".totals,.checkout").hide();
  }
  $("#cart-subtotal").html(subTotal.toFixed(2));
  $("#cart-total").html(grandTotal.toFixed(2));
  $("#cart-tax").html(tax.toFixed(2));
  $("#cart-shipping").html(shipping.toFixed(2));
  $("#user-balance").html(balance.toFixed(2));
}