package com.chat.client.thread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.chat.client.concurrent.ListSendMesagesToServer;
import com.chat.client.model.Client;

public class WriteEvent extends ClientEvent {
	private  OutputStream output;
	private PrintWriter writer;
	private ListSendMesagesToServer listSendMessagesToServer;
	public WriteEvent(Socket socket,Client client,ListSendMesagesToServer sendMessagesToServer) {
		// TODO Auto-generated constructor stub
		super(socket,client);
		this.listSendMessagesToServer=sendMessagesToServer;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			/*
			
			BufferedReader user_in = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter Nickname");
			String text = user_in.readLine();
			*/
			writer.println(client.getNickname());//send nickname to server.

			while(true) {
				
				//System.out.print("Enter Message");
				//text = user_in.readLine();
				
				List<String> sendMessages=listSendMessagesToServer.getSendMessagesToServer();
				if(sendMessages!=null) {
					for(int i=0;i<sendMessages.size();i++) {
						String msg=sendMessages.get(i);
						writer.println(msg);
					}
					listSendMessagesToServer.removeAllMessages();
				}	
				Thread.sleep(100);
			}
		
		} catch (Exception ex) {

			System.out.println("Error writing to server: " + ex.getMessage());
		}
		
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		try {
			 output = socket.getOutputStream();
	         writer = new PrintWriter(output, true);
	     } catch (IOException ex) {
	            System.out.println("Error getting in WriteEvent: " + ex.getMessage());
	            
	     }
		
	}

}
