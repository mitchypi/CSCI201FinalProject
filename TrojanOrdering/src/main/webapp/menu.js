var dat;
var log;


$(document).ready(function(){
console.log();
$.ajax({
	  url: "GetRest",
	  type: "GET",
	  //data: "{\"a\":\"b\"}",
	  dataType:"json",
	  success: function(data) {
		console.log("fetch good");
		console.log( data);
		log = data;
			$.ajax({
			  url: "MenuServlet",
			  type: "GET",
			  data: {"restaurant_id":log.restID},
			  dataType: "json",
			  success: function(data) {
				console.log("fetch good");
				console.log(data);
				dat = data;
				//need to edit parsing method, just a template
				//$("#add").append("<div id=\"div1\"><label id=\"headName\">"+data.name+"</label><br><div id=\"ii\"></div><img id=\"image\" src=\""+data.img+"\"></img><br>");
				//$("#add").append("<div id=\"div1\"><label id=\"headName\">In-N-Out</label><br><div id=\"ii\"></div><img id=\"image\" src=\"https://media.istockphoto.com/id/1457889029/photo/group-of-food-with-high-content-of-dietary-fiber-arranged-side-by-side.jpg?s=1024x1024&w=is&k=20&c=96MkVCuqUWOcMZ7vO5nG41rPufiSWlayTac_nsxXUTw=\"></img><br>")
				
				
				
				$("#headName").text(log.restName);
				$("#image").attr("src",log.restUrl);
				var key = 0;
				for(key in dat){
					$("#div1").append("<form><table><tbody><tr><th><label id=\"name\">"+dat[key].name+"</label></th><th><label id=\"price\">$"+dat[key].price.toFixed(2)+"</label></th><th><div class=\"box\"><a id=\""+key+"\" class=\"button\" href=\"#popup1\" onclick=\"ffunc(this)\">Add to Order</a></div></th></tr></tbody></table></form><br></div>");
				}
				

			},
			error: function(data)
			{
				console.log("fetch bad");
				console.log(data);
			}
		});
				
	},
	error: function(data)
	{
		console.log("fetch bad");
		console.log(data);
	}
});


$("#fg").on("submit", function(event){
		event.preventDefault();
		
		var inp = $("#fg").attr("class");
		var at = dat[inp];
		
		//var pp = $("#quantity").val();
		var ij = $("#info").attr("class");
		
		//var ap = {"quantity":pp};
		var ji = {"itemID":ij};//userID NEEEEEEEEEEEEEED
		var aa = {"userID":log.userID}//////////////////////////////neeeeeeeeeed
		//$.extend(at,ap);
		$.extend(at,ji);
		$.extend(at,aa);
		console.log(at);
		$.ajax({
			type: "GET",//feel like POST
			url: "ItemServlet",
			data: at,
			dataType: "json",
			success: function(data)
			{
				console.log("success");
				console.log(data);//can be random message
				alert("Added 1 "+at.name+" to cart");
	    		window.location.replace("menu.html");


					//window.location.replace("index.html");
					//window.location.replace("index.html");
			},
			error: function(data){
				console.log("failed");
				console.log(data);
					//window.location.replace("index.html");
			}
	   });
  });
});


function ffunc(e){

document.getElementById("menuName").textContent = dat[e.getAttribute("id")].name;
document.getElementById("nima").setAttribute("src", dat[e.getAttribute("id")].imgUrl) ;
console.log(dat[e.getAttribute("id")].imgUrl);

var classs = document.getElementById("fg").className;
console.log(classs);

document.getElementById("fg").classList.remove(classs);
document.getElementById("fg").classList.add(e.getAttribute("id"));

classs = document.getElementById("info").className;
console.log(classs);
document.getElementById("info").classList.remove(classs);
document.getElementById("info").classList.add(dat[e.getAttribute("id")].itemID);



}