		
var clientListMessageTimer = {};
var clientListNicknamesTimer = {};
var clientGetQueryResponseTimer = {};
var clientNickname = "";
$(document).ready(function() {

   $(document).delegate('#send_client_message', 'click', function(event) {
		event.preventDefault();
        var client_select_value=$('#client_slct').val();
        var client_message=$("#client_message").val().replace(/\s/g, '');
        if(!client_select_value || client_select_value==='' || !client_message || client_message===''){
	     $("#ok_msg_for_chat_panel").html("<span style='color: red'>All fields can't be empty!' </span>");
         setTimeout(function() { $("#ok_msg_for_chat_panel").html("") }, 3000);
         return;
        }
		var json_msg = {};
		json_msg["header"] = "sendClientMessage";
		json_msg["receivernickname"] = $('#client_slct').val();
		json_msg["sendernickname"] = clientNickname;
		json_msg["message"]= $("#client_message").val();

		$.ajax({
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: "/sendClientMessage",
			data: JSON.stringify(json_msg),
			cache: false,
			success: function(result) {
                addClientMessage(0,undefined);
				$("#ok_msg_for_chat_panel").html("<span style='color: green'>Your message is send to server successfully</span>");
				setTimeout(function() { $("#ok_msg_for_chat_panel").html("") }, 3000);
			},
			error: function(err) {
				$("#ok_msg_for_chat_panel").html("<span style='color: red'>Your message is not send to server. </span>");
			}
		});
	   
      
		
	});

 $(document).delegate('#sendQueryRequest', 'click', function(event) {
		event.preventDefault();

		var json_msg = {};
		json_msg["header"] = "sendQueryRequest";
		var last= $('#query_last_messages').val().replace(/\s/g, '');
		if(!last || last.length===0){
			last="0";
		}
		json_msg["last"] =last;
		json_msg["contain"]= $('#query_contain_text').val().replace(/\s/g, '');
		if($("#send_by_me").is(":checked")){
			json_msg["direction"]="send_by_me";
		}
		else if($("#send_to_me").is(":checked")){
			json_msg["direction"]="send_to_me";
		}
		json_msg["nickname"]= clientNickname;
	

		$.ajax({
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: "/sendQueryRequest",
			data: JSON.stringify(json_msg),
			cache: false,
			success: function(result) {

				$("#ok_msg_for_filter_panel").html("<span style='color: green'>Your message is send to server successfully</span>");
				setTimeout(function() { $("#ok_msg_for_filter_panel").html("") }, 3000);
				clientGetQueryResponseTimer=setInterval(getQueryResponse, 1000);
			},
			error: function(err) {
				$("#ok_msg_for_filter_panel").html("<span style='color: red'>Your message is not send to server. </span>");
			}
		});
	});

 

})
	function getClientInformation() {

		
		$.ajax({
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: "/getClientInformation",
			//data: JSON.stringify(json_data),
			cache: false,
			success: function(result) {
				if (result =='') {
				
				}
				else {
					
					insertClientInfo(result);
				}
			},
			error: function(err) {

			}
		});

	}
	function getClientListNicknames() {
		
		var json_data = {};
		json_data["header"] = "getClientListNicknames";
		$.ajax({
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: "/getClientListNicknames",
			data: JSON.stringify(json_data),
			cache: false,
			success: function(result) {
				if (result == '') {
				
				}
				else {
				
					clearInterval(clientListNicknamesTimer);
					insertClientNickNames(result);
				}
			},
			error: function(err) {

			}
		});
	}
	function getQueryResponse() {
		
		var json_data = {};
		json_data["header"] = "getQueryResponse";
		$.ajax({
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: "/getQueryResponse",
			data: JSON.stringify(json_data),
			cache: false,
			success: function(result) {
				if (result == '') {
					
				}
				else {
					
					clearInterval(clientGetQueryResponseTimer);
					insertQueryResponse(result);
				}
			},
			error: function(err) {

			}
		});
	}
	
	
	
	getClientInformation();
	getClientListNicknames();
	getClientListMessage();
	clientListMessageTimer = setInterval(getClientListMessage, 2000);
	clientListNicknamesTimer=setInterval(getClientListNicknames, 500);

	function getClientListMessage() {
		
		var json_data = {};
		json_data["header"] = "getClientListMessages";
	
		$.ajax({
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: "/getClientListMessages",
			data: JSON.stringify(json_data),
			cache: false,
			success: function(result) {

				if (result == '') {
				
				}
				else {
					//clearInterval(clientListMessageTimer);
					addClientMessage(1, result);
				}

				//console.log("List:"+JSON.stringify(result));
			},
			error: function(err) {

			}
		});

	}

function insertClientNickNames(json) {
	
	var json_obj=JSON.parse(json);
	var client_slct = document.getElementById("client_slct");
	for (var i = 0; i < json_obj.nicknames.length; i++) {
		
		var opt = document.createElement('option');
		opt.value = json_obj.nicknames[i];
		opt.innerHTML =json_obj.nicknames[i];
		client_slct.appendChild(opt);
	}
}

function insertQueryResponse(json){
	
	var json_element=JSON.parse(json);
	var table = document.getElementById("query_results");
    for(var i = 1;i<table.rows.length;){
            table.deleteRow(i);
    }


	for (var i = 0; i < json_element.queryresult.length; i++) {

			var nickname = json_element.queryresult[i].nickname;
			var message = json_element.queryresult[i].message;
			var table_len = (table.rows.length);
			table.insertRow(table_len).outerHTML = "<tr>" +
				"<td><b>" + nickname + "</b></td>" +
				"<td>" + message + "</td>" +
				"</tr>";

	}
}


function insertClientInfo(json) {
	
	clientNickname = json.nickname;
	var client_info = document.getElementById("client_info");
	client_info.innerHTML = "<b>[" + json.nickname + ", " + json.ipAddress + ", " + json.port + "]<b/>";
}



function addClientMessage(type, json) {
 
	if (type == 0) {

		var table = document.getElementById("client_all_messages");
		var table_len = (table.rows.length - 1);
		//var client_slct_value=document.getElementById("client_slct").value;
		var client_message = document.getElementById("client_message").value;

		table.insertRow(table_len).outerHTML = "<tr>" +
			"<td>" + clientNickname + "</td>" +
			"<td>" + client_message + "</td>" +
			"</tr>";

	}
	else if (type == 1) {

		var table = document.getElementById("client_all_messages");
	    var json_obj=JSON.parse(json);
		var sendernickname = json_obj.sendernickname;
		var message = json_obj.message;
		var table_len = (table.rows.length) - 1;
		table.insertRow(table_len).outerHTML = "<tr>" +
				"<td><b>" + sendernickname + "</b></td>" +
				"<td>" + message + "</td>" +
				"</tr>";
	}

}

