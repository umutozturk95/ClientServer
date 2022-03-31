package com.chat.server.service;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.HashMap;

import com.chat.server.model.ChatMessage;
import com.chat.server.model.Client;
import com.chat.server.thread.ClientThread;
import com.chat.server.repository.ChatMessageRepository;
import com.chat.server.repository.ChatMessageRepositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("tcpService")
public class TCPServer  implements Server{
	@Autowired
	private ChatMessageRepositoryImpl chatMessageRepository;
    private  ConcurrentHashMap<String, Client> all_clients;
    private int tcpPort;
    
    public TCPServer() {
    	all_clients=new  ConcurrentHashMap<String, Client>();
    }
	public TCPServer(int port) {
		// TODO Auto-generated constructor stub
		this.tcpPort=port;
		all_clients=new   ConcurrentHashMap<String, Client>();
	}
	public void setTCPPort(int port) {
		this.tcpPort=port;
	}
	public int getTCPPort() {
		return this.tcpPort;
	}
	public  ConcurrentHashMap<String, Client> getAllClients(){
		return all_clients;
	}
	
  @Override 
	public void startCommunication() {
		
	  System.out.println("Server is listening on port " + tcpPort);
	  
 	  try (ServerSocket serverSocket = new ServerSocket(tcpPort)) {
 		       
           while (true) {
               Socket socket = serverSocket.accept();
               new ClientThread(socket,all_clients,chatMessageRepository).start();
           }

       } catch (IOException ex) {
           System.out.println("Server exception: " + ex.getMessage());
           ex.printStackTrace();
       }
		
	}
  
  
}
