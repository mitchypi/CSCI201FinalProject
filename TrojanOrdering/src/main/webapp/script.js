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
        console.log(userID);
  		console.log(balance);

        var i = 0;
        const items = [];
        fetch('/TrojanOrdering/checkout?userID=' + userID)
        .then(response => response.json())
        .then(response => {
          const data = response.data; //by reassigning the json to a new data vay, it should be an array by default which we can use forEach on
          data.forEach(item => {
          const cartItemTemp = new cartItem(item.name, item.price, 1);
          var itemExists = false;
          items.forEach(existingItem => {
            if (existingItem.name == cartItemTemp.name) {
              itemExists = true;
              existingItem.quantity++;
              existingItem.total += cartItemTemp.price;
            }
          });
          if (!itemExists) {
            items.push(cartItemTemp);
          }
          
        });

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
            this.price.toFixed(2) +
            "</td>" +
            "        <td>" +
            '          <input class="input is-primary cart-item-qty" style="width:100px" type="number" min="1" value="' +
            this.quantity +
            '" data-price="' +
            this.price +
            '">' +
            "        </td>" +
            '        <td class="cart-item-total">$' +
            this.total.toFixed(2) +
            "</td>" +
            "        <td>" +
            '          <a class="button is-small">Remove</a>' +
            "        </td>" +
            "      </tr>";
        });

        html += "</tbody>";
        $("#cart-items").html(html);

        recalculateCart();
      });
    },
    error: function(xhr, status, error) {
        console.log('Error:', error);
    }
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
  //$("#user-balance").html(balance.toFixed(2));
}