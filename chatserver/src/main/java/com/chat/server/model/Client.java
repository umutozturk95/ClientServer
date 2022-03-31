package com.chat.server.model;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
     
	private String nickName;
	private Socket socket;
	private OutputStream outputStream=null;
	private PrintWriter writer;
	public Client(String nickName,Socket socket) {
		// TODO Auto-generated constructor stub
	   this.nickName=nickName;
	   this.socket=socket;
	    try {
			this.outputStream = socket.getOutputStream();
			this.writer = new PrintWriter(outputStream, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Client() {
		
		
	}
	public String getNickName() {
		return this.nickName;
	}
	public void setNickName(String nickName) {
		this.nickName=nickName;
	}
	public void setSocket(Socket socket) {
		this.socket=socket;
	}
	public Socket getSocket() {
		return this.socket;
	}
	public void sendMesageToClient(String message) {
		try {
			writer.println(message);
		}
		catch(Exception ex) {
			System.out.println("Error Message: "+ex.getMessage());
		}
	}

}
