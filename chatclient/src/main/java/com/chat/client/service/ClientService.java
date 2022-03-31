package com.chat.client.service;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chat.client.model.Client;
import com.chat.client.thread.ClientEvent;
import com.chat.client.thread.ReadEvent;
import com.chat.client.thread.WriteEvent;
import java.util.concurrent.ConcurrentHashMap;
import com.chat.client.concurrent.ListReceivedMessagesFromServer;
import com.chat.client.concurrent.ListSendMesagesToServer;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
@Service
public class ClientService {

	private Socket socket;
	private ClientEvent readEvent;
	private ClientEvent writeEvent;
	String[] args;
	private Client client;
	private  ListReceivedMessagesFromServer listReceivedMessagesFromServer;
	private ListSendMesagesToServer listSendMessagesToServer  ;
	public ClientService() {
		// TODO Auto-generated constructor stub
		//this.args=args;
		//System.out.println(Arrays.toString(args));
	
		listReceivedMessagesFromServer=new ListReceivedMessagesFromServer();
		listSendMessagesToServer= new ListSendMesagesToServer();
	}
	
	public void setArgs(String[]args) {
		this.args=args;
	}
	public void init() {
		
	
			String nickname = args[0].replaceAll("\\s", "").trim();
			String ipAddress = args[1].replaceAll("\\s", "").trim();
			int port = Integer.valueOf(args[2]);
			
			
			System.out.println("---------Client------------------");
			System.out.println("Nickname:"+nickname+" ipAdress:"+ipAddress+" port:"+port);
			System.out.println("---------Client------------------");
			
			try {
				
				client=new Client(nickname,"");
				client.setIpAddress(ipAddress);
				client.setPort(port);
				
				socket = new Socket(ipAddress, port);
				readEvent = new ReadEvent(socket,client,listReceivedMessagesFromServer);
				readEvent.init();
				readEvent.start();

				writeEvent = new WriteEvent(socket,client,listSendMessagesToServer);
				writeEvent.init();
				writeEvent.start();
			
			} catch (UnknownHostException e) {
				System.out.println("Error getting in ClientService: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("Error getting in ClientService: " + e.getMessage());
			}

	
	}
	public Client getClient() {
		return this.client;
	}
	public void setClient(Client client) {
		this.client=client;
	}
	public ListReceivedMessagesFromServer getListReceivedMessagesFromServer() {
	  return this.listReceivedMessagesFromServer;
	}
	public ListSendMesagesToServer getListSendMesagesToServer() {
		  return this.listSendMessagesToServer;
	}
}
