$(document).ready(function(){
	
	var dat;
	$.ajax({
  		url: "MenuServlet",
  		type: "GET",
  		success: function(data) {
			console.log("fetch good");
			console.log(data);
			dat = data;
            //need to edit parsing method, just a template
            $("#add").append("<div id=\"div1\"><label id=\"headName\">"+data.name+"</label><br><div id=\"ii\"></div><img id=\"image\" src=\""+data.img+"\"></img><br>");


			var key = 0;
			for(key in data.menu){
				$("#add").append("<form id=\""+key+"\"><table><tbody><tr><th><label id=\"name\">"+data.menu[key].name+"</label></th><th><label id=\"price\">$"+data.menu[key].price+"</label></th><th><input id=\"button\" type=\"submit\" value=\"Add to Order\" onclick=\"mu(this)\"></input></th></tr></tbody></table></form><br>");
			}
		},
		error: function(data)
		{
			console.log("fetch bad");
			console.log(data);
		}
	});

    //TODO:need to grab data of clicked food and send to servlet

});