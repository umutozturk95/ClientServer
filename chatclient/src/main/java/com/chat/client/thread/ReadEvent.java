package com.chat.client.thread;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;  
import org.json.simple.JSONValue;  
import com.chat.client.model.Client;
import com.chat.client.concurrent.ListReceivedMessagesFromServer;

public class ReadEvent extends ClientEvent {

	private BufferedReader user_in;
	private PrintWriter writer;
	private InputStream input;
	private ListReceivedMessagesFromServer listReceivedMessagesFromServer;

	public ReadEvent(Socket socket, Client client, ListReceivedMessagesFromServer receivedMessagesFromServer) {
		// TODO Auto-generated constructor stub
		super(socket, client);
		this.listReceivedMessagesFromServer = receivedMessagesFromServer;
	}

	@Override
	public void run() {
		try {
			String response;
			while (true) {
				response = user_in.readLine();
				parseReceivedMessageFromServer(response);
				Thread.sleep(100);
				 
			}
		} catch (Exception ex) {
			System.out.println("Error reading from server: " + ex.getMessage());
		} 

	}

	@Override
	public void init() {

		this.socket = socket;
		try {
			input = socket.getInputStream();
			user_in = new BufferedReader(new InputStreamReader(input));
		} catch (IOException ex) {
			System.out.println("Error getting in ReadEvent: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void parseReceivedMessageFromServer(String jsonMessage) {

		String header = parseJSONHeader(jsonMessage, "header");
		try {
			switch (header) {

			case "getClientListNicknames": {
				listReceivedMessagesFromServer.addReceviedMessages("getClientListNicknames",jsonMessage);
				break;
			}

			case "sendClientMessage": {
				listReceivedMessagesFromServer.addReceviedMessages("getClientListMessages",jsonMessage);																							
				break;
			}
			case "sendQueryRequest" :{
				listReceivedMessagesFromServer.addReceviedMessages("getQueryResponse",jsonMessage);
				break;
			}
			
			default:
				break;
			}

		} catch (Exception ex) {
			System.out.println("parseReceivedMessageFromServer exception : " + ex.getMessage());
		}

		finally {
		}
	}
	public String parseJSONHeader(String jsonMessage, String key) {

		Object obj = JSONValue.parse(jsonMessage);
		JSONObject jsonObject = (JSONObject) obj;
		String header = (String) jsonObject.get(key);

		return header;
	}
 
}
