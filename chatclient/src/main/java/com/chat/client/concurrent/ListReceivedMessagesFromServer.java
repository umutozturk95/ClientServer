package com.chat.client.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import com.chat.client.model.Client;
public class ListReceivedMessagesFromServer {
    private Semaphore mutex;
    private  ConcurrentHashMap<String,String> messages;
	public ListReceivedMessagesFromServer() {
		// TODO Auto-generated constructor stub
	   mutex=new Semaphore(1);
	   messages=new ConcurrentHashMap<>();
	}

	public void addReceviedMessages(String header, String responseMessage) {

		messages.put(header, responseMessage);
	
	}

	public String getMessagesFromServer(String header) {
		 String response=null;
		 if(messages.containsKey(header)) {
			 response=new String( messages.get(header));
		 }
		 return response;
	}

	public void removeClientMessages(String header) {

		if (messages.containsKey(header)) {
			messages.remove(header);
		}
	}

}
