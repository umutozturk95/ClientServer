package com.chat.server.thread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.chat.server.model.ChatMessage;
import com.chat.server.model.Client;
import com.chat.server.repository.ChatMessageRepository;
import com.chat.server.repository.ChatMessageRepositoryImpl;
import com.chat.server.service.Server;
import com.chat.server.service.TCPServer;

public class ClientThread extends Thread {
	
	private Client client;
	private TCPServer server;
    private ConcurrentHashMap<String, Client> all_clients;
    private InputStream input;
    private BufferedReader reader;
    private ChatMessageRepositoryImpl chatMessageRepository;
	
    public ClientThread(Socket socket, ConcurrentHashMap<String, Client> all_clients,ChatMessageRepositoryImpl chatMessageRepository) {
		// TODO Auto-generated constructor stub
		client=new Client("",socket);
		this.all_clients=all_clients;
		this.chatMessageRepository=chatMessageRepository;
        init();
	}
	
    public void init() {
    	try {
		     input =client.getSocket().getInputStream();
			 reader = new BufferedReader(new InputStreamReader(input));
    	}
    	catch (IOException ex) {
			System.out.println("Init exception: " + ex.getMessage());
			ex.printStackTrace();
		}
    	
    	
    }
	public void run() {
		try {
		
			String nickname=reader.readLine();
			System.out.println(nickname+" connects to server!");
			
			if(!all_clients.containsKey(nickname)) {
			    client.setNickName(nickname);
				all_clients.put(nickname,client);
			}
			

			String jsonMessage="";
			while(true) {
				jsonMessage = reader.readLine();		
				SendMessageToClient(jsonMessage);
			
			}
			//socket.close();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	 
	public void SendMessageToClient(String jsonMessage) {
		String header=getKeyFromJSONMessage(jsonMessage,"header");

		switch (header) {

		case "getClientListNicknames": {
			String allClients = getCurrentClientNickNames(header);
			client.sendMesageToClient(allClients);
			// writer.flush();
			break;
		}

		case "sendClientMessage": {
			String receiverNickname = getKeyFromJSONMessage(jsonMessage, "receivernickname");
			String senderNickname = getKeyFromJSONMessage(jsonMessage, "sendernickname");
			String message = getKeyFromJSONMessage(jsonMessage, "message");

			ChatMessage newChatMessage = new ChatMessage(senderNickname, receiverNickname, message);
			saveChatMessage(newChatMessage);
			Client receiverClient = all_clients.get(receiverNickname);
			receiverClient.sendMesageToClient(jsonMessage);
			break;
		}

		case "sendQueryRequest": {
			List<ChatMessage> chatMessages = sendQueryRequest(jsonMessage);
			String queryResultAsJSON = convertQueryResponseToJSON(chatMessages, header);
			client.sendMesageToClient(queryResultAsJSON);

			break;
		}

		default:
			break;
		}
		
}
	
	
	public List<ChatMessage> sendQueryRequest(String jsonMessage) {
	   int top=Integer.valueOf(getKeyFromJSONMessage(jsonMessage, "last"));
	   String containsText=getKeyFromJSONMessage(jsonMessage, "contain");
	   String messageDirection=getKeyFromJSONMessage(jsonMessage, "direction");
	   String nickName=getKeyFromJSONMessage(jsonMessage, "nickname");
	   
	   List<ChatMessage>chatMessages = chatMessageRepository.queryRecords(nickName,top,containsText,messageDirection);
	   return chatMessages;
	}
   
	public String convertQueryResponseToJSON(List<ChatMessage> chatMessages,String header) {
		
		  JSONObject object = new JSONObject();
		  object.put("header", header);
		  JSONArray arrayElement = new JSONArray();
            
		  for (int i=0;i<chatMessages.size();i++) {
			    ChatMessage chat=chatMessages.get(i);
				JSONObject subObj=new JSONObject();
				subObj.put("nickname",chat.getReceiverNickname());
				subObj.put("message", chat.getMessage());
				arrayElement.add(subObj);
			}
	

		  object.put("queryresult", arrayElement);
		  String jsonText = JSONValue.toJSONString(object);
		  return jsonText;
	}
	public void saveChatMessage(ChatMessage chatMessage) {
		chatMessageRepository.saveChatMessage(chatMessage);
	}
	
	public String getCurrentClientNickNames(String header) {
		
		JSONObject object = new JSONObject();
		object.put("header", header);
		JSONArray arrayElementOneArray = new JSONArray();

		for (HashMap.Entry<String, Client> entry : all_clients.entrySet()) {
			// System.out.println("Key = " + entry.getKey());
			String nickname=entry.getKey();
			if(!client.getNickName().equals(nickname)) {
			  arrayElementOneArray.add(entry.getKey());
			}
		}

		object.put("nicknames", arrayElementOneArray);
		String jsonText = JSONValue.toJSONString(object);
		return jsonText;
	}
	
	
	public String getKeyFromJSONMessage(String jsonMessage,String key) {
		
		 Object obj=JSONValue.parse(jsonMessage);  
		 JSONObject jsonObject = (JSONObject) obj;  
		 String header = (String) jsonObject.get(key);  
		 
		return header;
	}


}
