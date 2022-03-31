package com.chat.client.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.chat.client.model.Client;
import com.chat.client.service.ClientService;


@RestController
//@CrossOrigin(origins = "*")
public class RequestController {

	@Autowired
	private ClientService clientService;
	public RequestController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(
		    value = "/getQueryResponse", 
		    method = RequestMethod.POST,
		    consumes = "application/json")
	public ResponseEntity<String> getQueryResponse(@RequestBody String jsonMessage) {

		String header="getQueryResponse";
		String responseMessage=clientService.getListReceivedMessagesFromServer().getMessagesFromServer(header);
		if(responseMessage!=null) {
			clientService.getListReceivedMessagesFromServer().removeClientMessages(header);		
		}
		
		return new ResponseEntity<String>(responseMessage,HttpStatus.OK);
	}
	
	@PostMapping("/sendQueryRequest")
	public ResponseEntity<Void> saveOrUpdateCompany(@RequestBody String jsonMessage) {
		String header="sendQueryRequest";
		clientService.getListSendMesagesToServer().addSendMessage(header, jsonMessage);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
     
	@RequestMapping(
		    value = "/getClientInformation", 
		    method = RequestMethod.POST,
		    consumes = "application/json")
	public ResponseEntity<Client> getClientInformation() {
		
		return new ResponseEntity<Client>(clientService.getClient(),HttpStatus.OK);
	}

	@RequestMapping(
		    value = "/getClientListNicknames", 
		    method = RequestMethod.POST,
		    consumes = "application/json")
	public ResponseEntity<String> getClientNicknames(@RequestBody String jsonMessage) {
		
		String header="getClientListNicknames";
		String responseMessage=clientService.getListReceivedMessagesFromServer().getMessagesFromServer(header);
		if(responseMessage!=null) {
			clientService.getListReceivedMessagesFromServer().removeClientMessages(header);		
		}
		else {
			clientService.getListSendMesagesToServer().addSendMessage(jsonMessage, jsonMessage);
		}
		return new ResponseEntity<String>(responseMessage,HttpStatus.OK);
	
	}
	@RequestMapping(
		    value = "/getClientListMessages", 
		    method = RequestMethod.POST,
		    consumes = "application/json")
	public ResponseEntity<String> getClientListMessage(@RequestBody String jsonMessage) {
	
		String header="getClientListMessages";
		String responseMessage=clientService.getListReceivedMessagesFromServer().getMessagesFromServer(header);
		if(responseMessage!=null) {
			clientService.getListReceivedMessagesFromServer().removeClientMessages(header);		
		}
		return new ResponseEntity<String>(responseMessage,HttpStatus.OK);
		
	}
	
	@PostMapping("/sendClientMessage")
	public ResponseEntity<Void> sendClientMessage(@RequestBody String jsonMessage) {
		String header="sendClientMessage";
		clientService.getListSendMesagesToServer().addSendMessage(header, jsonMessage);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
